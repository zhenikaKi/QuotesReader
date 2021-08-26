package ru.kirea.quotesreader.data

import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.entities.Quote

interface QuoteRepository {

    //Получить цитату с сайта
    fun getQuote(): Single<Quote>
}