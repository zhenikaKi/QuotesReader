package ru.kirea.quotesreader.di

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.kirea.quotesreader.App
import ru.kirea.quotesreader.di.modules.ApiModule
import ru.kirea.quotesreader.di.modules.DBModule
import ru.kirea.quotesreader.di.modules.DataModule
import ru.kirea.quotesreader.di.modules.UIModule
import ru.kirea.quotesreader.helpers.schedules.AppSchedulers
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApiModule::class,
    UIModule::class,
    DataModule::class,
    DBModule::class])
interface AppComponent: AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        //это singleton-зависимости
        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigationHolder(navigatorHolder: NavigatorHolder): Builder

        @BindsInstance
        fun withAppScheduler(appSchedulers: AppSchedulers): Builder

        fun build(): AppComponent
    }
}