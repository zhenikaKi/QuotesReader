package ru.kirea.quotesreader.data.db

import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.entities.Quote
import javax.inject.Inject

class QuoteDBImpl @Inject constructor(private val storageDB: StorageDB):  QuoteDB {

    //вставить цитату
    override fun insert(quote: Quote) = storageDB.quoteDao().insert(quote)

    //получить просмотренные цитаты
    override fun getQuotes(): Single<List<Quote>> = storageDB.quoteDao().getQuotes()

    //удалить цитату
    override fun delete(quote: Quote) = storageDB.quoteDao().delete(quote)
}