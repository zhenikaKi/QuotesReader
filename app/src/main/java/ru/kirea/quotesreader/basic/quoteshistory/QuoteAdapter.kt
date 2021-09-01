package ru.kirea.quotesreader.basic.quoteshistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kirea.quotesreader.data.entities.Quote
import ru.kirea.quotesreader.databinding.QuoteHistoryItemBinding
import ru.kirea.quotesreader.extensions.format
import java.util.*
import javax.inject.Inject

class QuoteAdapter @Inject constructor(
    private val presenter: QuoteHistoryListPresenter
    ): RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(QuoteHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            presenter.bindView(holder.apply { currentPosition = position })

    inner class ViewHolder(private val binding: QuoteHistoryItemBinding):
        RecyclerView.ViewHolder(binding.root), QuoteItemView {

        override var currentPosition = -1

        override fun setData(quote: Quote) {
            with(binding) {
                quoteText.text = quote.quoteText
                quoteAuthor.text = quote.quoteAuthor
                date.text = Date(quote.viewed).format("dd.MM.yyyy HH:mm")
            }
        }
    }
}