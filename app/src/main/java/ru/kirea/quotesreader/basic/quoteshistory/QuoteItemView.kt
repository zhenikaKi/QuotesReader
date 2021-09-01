package ru.kirea.quotesreader.basic.quoteshistory

import ru.kirea.quotesreader.data.entities.Quote

interface QuoteItemView {
    var currentPosition: Int

    fun setData(quote: Quote)
}