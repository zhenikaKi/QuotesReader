package ru.kirea.quotesreader.data.db.repositories

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.entities.Quote

interface QuoteDB {

    //вставить цитату
    fun insert(quote: Quote): Completable

    //получить просмотренные цитаты
    fun getQuotes(): Single<List<Quote>>

    //удалить цитату
    fun delete(quote: Quote): Completable
}