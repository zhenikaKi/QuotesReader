package ru.kirea.quotesreader.basic.quoteshistory

import dagger.assisted.AssistedFactory

@AssistedFactory
interface QuotesHistoryPresenterFactory {
    fun create(): QuotesHistoryPresenter
}