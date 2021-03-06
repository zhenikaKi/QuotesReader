package ru.kirea.quotesreader.basic.main

import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import moxy.MvpView
import ru.kirea.quotesreader.basic.BasePresenter
import ru.kirea.quotesreader.basic.quote.QuoteScreen

class MainPresenter @AssistedInject constructor(
    router: Router
): BasePresenter<MvpView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //показываем окно со случайной цитатой
        router.newRootScreen(QuoteScreen())
    }
}