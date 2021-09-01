package ru.kirea.quotesreader.basic.main

import dagger.assisted.AssistedFactory

@AssistedFactory
interface MainPresenterFactory {
    fun create(): MainPresenter
}