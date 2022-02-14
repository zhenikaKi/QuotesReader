package ru.kirea.quotesreader.di.modules

import dagger.Binds
import dagger.Module
import ru.kirea.quotesreader.data.QuoteRepository
import ru.kirea.quotesreader.data.QuoteRepositoryLocalImpl
import javax.inject.Singleton

@Module
interface QuoteRepositoryModule {
    @Singleton
    @Binds
    fun bindQuoteRepository(repository: QuoteRepositoryLocalImpl): QuoteRepository
}