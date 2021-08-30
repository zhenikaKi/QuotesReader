package ru.kirea.quotesreader.basic.quoteshistory

import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.kotlin.plusAssign
import ru.kirea.quotesreader.basic.BasePresenter
import ru.kirea.quotesreader.data.db.repositories.QuoteDB
import ru.kirea.quotesreader.data.entities.Quote
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers

class QuotesHistoryPresenter @AssistedInject constructor(
    router: Router,
    private val quoteDB: QuoteDB,
    private val schedulers: AppSchedulers
) : BasePresenter<QuotesHistoryView>(router), QuoteHistoryListPresenter {

    private val quotes = mutableListOf<Quote>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    //привязка элемента к view
    override fun bindView(view: QuoteItemView) {
        view.setData(quotes[view.currentPosition])
    }

    //количество элементов в списке
    override fun getCount() = quotes.size

    private fun loadData() {
        //подписываемся на поток списка пользователей
        disposables +=
                quoteDB
                .getQuotes()
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background()) //обработку делаем в отдельном потоке
                .subscribe(
                    { quotesDB ->
                        quotes.addAll(quotesDB)
                        viewState.updateList()
                    },
                    { exception -> viewState.showException(exception) }
                )
    }
}