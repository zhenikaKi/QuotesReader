package ru.kirea.quotesreader.basic.main

import dagger.assisted.AssistedFactory

@AssistedFactory
interface MainFactory {
    fun create(): MainPresenter
}