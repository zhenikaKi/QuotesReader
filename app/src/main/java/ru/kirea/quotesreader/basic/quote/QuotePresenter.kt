package ru.kirea.quotesreader.basic.quote

import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.kirea.quotesreader.basic.BasePresenter
import ru.kirea.quotesreader.basic.quoteshistory.QuotesHistoryScreen
import ru.kirea.quotesreader.data.QuoteRepository
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.db.repositories.QuoteDB
import ru.kirea.quotesreader.data.db.repositories.SettingDB
import ru.kirea.quotesreader.data.events.BaseHandler
import ru.kirea.quotesreader.data.events.UpdatePeriodEvent
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers
import java.util.concurrent.TimeUnit

class QuotePresenter @AssistedInject constructor(
    router: Router,
    private val quoteDB: QuoteDB,
    private val settingDB: SettingDB,
    private val quoteRepository: QuoteRepository,
    private val schedulers: AppSchedulers,
    private val baseHandler: BaseHandler
): BasePresenter<QuoteView>(router) {

    //таймер для автообновления и время автообновления
    private val timer = CompositeDisposable()
    private var periodUpdate = StorageDB.PERIOD_UPDATE_DEFAULT
    private var reverseTimerValue = StorageDB.PERIOD_UPDATE_DEFAULT

    override fun attachView(view: QuoteView?) {
        super.attachView(view)
        viewState.init()

        //подписываемся на изменения периода обновлеия цитат
        disposables += baseHandler.addHandler(UpdatePeriodEvent::class) { updatePeriodEvent ->
            periodUpdate  = updatePeriodEvent.newPeriod
            reverseTimerValue = periodUpdate
        }

        //загружаем цитату
        loadData()

        //получаем время обновления таймера
        loadPeriodUpdate()

        //запускаем таймер для обновления цитат
        initTimer()
    }

    override fun detachView(view: QuoteView?) {
        super.detachView(view)
        timer.clear()
    }

    //обновить цитату вручную
    fun updateQuote() {
        reverseTimerValue = periodUpdate
        loadData()
    }

    //открыть историю просмотра цитат
    fun openHistory() {
        router.navigateTo(QuotesHistoryScreen())
    }

    private fun loadData() {
        viewState.startUpdateQuote()
        disposables += quoteRepository.getQuote()
            .observeOn(schedulers.main())
            .subscribeOn(schedulers.background()) //обработку делаем в отдельном потоке
            .map { quote ->
                //сохраним полученную цитату
                quote.viewed = System.currentTimeMillis()
                quoteDB
                    .insert(quote)
                    .observeOn(schedulers.main())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {},
                        { exception -> viewState.showException(exception) }
                    )
                return@map quote
            }
            .subscribe(
                //уведомляем view о том, что получили цитату
                { quote ->
                    viewState.endUpdateQuote()
                    viewState.showQuote(quote)
                },

                { exception ->
                    viewState.endUpdateQuote()
                    viewState.showException(exception)
                }
            )
    }

    //получить время обовления таймера
    private fun loadPeriodUpdate() {
        disposables += settingDB.getSetting(StorageDB.PARAM_PERIOD_UPDATE)
            .observeOn(schedulers.main())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { setting ->
                    setting?.let {
                        periodUpdate = it.value.toInt()
                        initTimer()
                    } ?: StorageDB.PERIOD_UPDATE_DEFAULT
                    reverseTimerValue = periodUpdate
                },

                //при ошибках задаем период по умолчанию
                {
                    periodUpdate = StorageDB.PERIOD_UPDATE_DEFAULT
                    reverseTimerValue = periodUpdate
                }
            )
    }

    //запустить таймер
    private fun initTimer() {
        timer.clear()
        timer += Observable.interval(1, 1, TimeUnit.SECONDS)
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .subscribe{ processTimer() }
    }

    //обработать тик таймера
    private fun processTimer() {
        --reverseTimerValue
        if (reverseTimerValue == 0) {
            reverseTimerValue = periodUpdate
            loadData()
        }
        viewState.showReverseTimer(reverseTimerValue)
    }
}