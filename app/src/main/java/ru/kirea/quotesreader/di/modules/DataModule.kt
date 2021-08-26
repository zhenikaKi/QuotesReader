package ru.kirea.quotesreader.di.modules

import dagger.Binds
import dagger.Module
import ru.kirea.quotesreader.data.QuoteRepository
import ru.kirea.quotesreader.data.QuoteRepositoryImpl
import ru.kirea.quotesreader.data.db.QuoteDB
import ru.kirea.quotesreader.data.db.QuoteDBImpl
import javax.inject.Singleton

@Module
interface DataModule {
    @Singleton
    @Binds
    fun bindQuoteRepository(repository: QuoteRepositoryImpl): QuoteRepository

    @Singleton
    @Binds
    fun bindQuoteDB(quoteDB: QuoteDBImpl): QuoteDB
}