package ru.kirea.quotesreader.basic.quote

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import ru.kirea.quotesreader.basic.ExceptionView
import ru.kirea.quotesreader.data.entities.Quote

@SingleState
interface QuoteView: MvpView, ExceptionView {

    //проинициализировать view
    fun init()

    //начать обновление цитаты
    fun startUpdateQuote()

    //завершить обновление цитаты
    fun endUpdateQuote()

    //показать полученную цитату
    fun showQuote(quote: Quote)
}