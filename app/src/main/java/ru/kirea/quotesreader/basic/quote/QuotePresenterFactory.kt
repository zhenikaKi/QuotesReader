package ru.kirea.quotesreader.basic.quote

import dagger.assisted.AssistedFactory

@AssistedFactory
interface QuotePresenterFactory {
    fun create(): QuotePresenter
}