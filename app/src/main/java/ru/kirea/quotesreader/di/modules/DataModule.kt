package ru.kirea.quotesreader.di.modules

import dagger.Binds
import dagger.Module
import ru.kirea.quotesreader.data.QuoteRepository
import ru.kirea.quotesreader.data.QuoteRepositoryImpl
import ru.kirea.quotesreader.data.db.repositories.QuoteDB
import ru.kirea.quotesreader.data.db.repositories.QuoteDBImpl
import ru.kirea.quotesreader.data.db.repositories.SettingDB
import ru.kirea.quotesreader.data.db.repositories.SettingDBImpl
import javax.inject.Singleton

@Module
interface DataModule {
    @Singleton
    @Binds
    fun bindQuoteRepository(repository: QuoteRepositoryImpl): QuoteRepository

    @Singleton
    @Binds
    fun bindQuoteDB(quoteDB: QuoteDBImpl): QuoteDB

    @Singleton
    @Binds
    fun bindSettingDB(settingDB: SettingDBImpl): SettingDB
}