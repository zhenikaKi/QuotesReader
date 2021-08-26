package ru.kirea.quotesreader.basic.quote

import dagger.assisted.AssistedFactory

@AssistedFactory
interface QuoteFactory {
    fun create(): QuotePresenter
}