package ru.kirea.quotesreader.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.kirea.quotesreader.basic.main.MainActivity
import ru.kirea.quotesreader.basic.quote.QuoteFragment

@Module
interface UIModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindQuoteFragment(): QuoteFragment
}