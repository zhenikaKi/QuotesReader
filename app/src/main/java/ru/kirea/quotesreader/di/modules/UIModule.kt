package ru.kirea.quotesreader.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.kirea.quotesreader.basic.main.MainActivity
import ru.kirea.quotesreader.basic.quote.QuoteFragment
import ru.kirea.quotesreader.basic.quoteshistory.QuotesHistoryFragment
import ru.kirea.quotesreader.basic.setting.SettingFragment

@Module
interface UIModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindQuoteFragment(): QuoteFragment

    @ContributesAndroidInjector
    fun bindQuotesHistoryFragment(): QuotesHistoryFragment

    @ContributesAndroidInjector
    fun bindSettingFragment(): SettingFragment
}