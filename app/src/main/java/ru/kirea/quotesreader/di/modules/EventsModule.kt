package ru.kirea.quotesreader.di.modules

import dagger.Module
import dagger.Provides
import ru.kirea.quotesreader.data.events.BaseHandler
import javax.inject.Singleton

@Module
class EventsModule {

    @Singleton
    @Provides
    fun providerBaseHandler() = BaseHandler()
}