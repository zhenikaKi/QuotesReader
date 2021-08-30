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
import ru.kirea.quotesreader.data.db.repositories.QuoteDB
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers
import java.util.concurrent.TimeUnit

class QuotePresenter @AssistedInject constructor(
    router: Router,
    private val quoteDB: QuoteDB,
    private val quoteRepository: QuoteRepository,
    private val schedulers: AppSchedulers
): BasePresenter<QuoteView>(router) {

    //таймер для автообновления
    private val timer = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        //загружаем цитату
        loadData()
    }

    override fun attachView(view: QuoteView?) {
        super.attachView(view)

        //запускаем таймер для обновления цитат
        timer += Observable.interval(30, 30, TimeUnit.SECONDS)
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .subscribe{ loadData() }

    }

    override fun detachView(view: QuoteView?) {
        super.detachView(view)
        timer.clear()
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

    //обновить цитату вручную
    fun updateQuote() {
        loadData()
    }

    //открыть историю просмотра цитат
    fun openHistory() {
        router.navigateTo(QuotesHistoryScreen())
    }
}