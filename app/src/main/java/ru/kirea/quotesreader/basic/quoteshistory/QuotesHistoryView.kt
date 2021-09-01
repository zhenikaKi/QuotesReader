package ru.kirea.quotesreader.basic.quoteshistory

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import ru.kirea.quotesreader.basic.ExceptionView

@SingleState
interface QuotesHistoryView: MvpView, ExceptionView {

    //проинициализировать view
    fun init()

    //обновить список цитат
    fun updateList()
}