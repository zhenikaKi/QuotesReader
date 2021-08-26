package ru.kirea.quotesreader.data

import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.api.ForismaticApi
import ru.kirea.quotesreader.data.db.QuoteDB
import ru.kirea.quotesreader.data.entities.Quote
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val forismaticApi: ForismaticApi,
    private val quoteDB: QuoteDB
): QuoteRepository {

    //Получить цитату с сайта
    override fun getQuote(): Single<Quote> = forismaticApi
        .getQuote()
        .map { quote ->
            //сохраним полученную цитату
            quote.viewed = System.currentTimeMillis()
            quoteDB.insert(quote)

            return@map quote
        }
}