package ru.kirea.quotesreader.basic.quoteshistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import ru.kirea.quotesreader.basic.listeners.BackButtonListener
import ru.kirea.quotesreader.databinding.QuotesHistoryFragmentBinding
import ru.kirea.quotesreader.extensions.showToast
import ru.kirea.quotesreader.helpers.di.BaseDaggerFragment
import javax.inject.Inject

class QuotesHistoryFragment: BaseDaggerFragment(), QuotesHistoryView, BackButtonListener {

    @Inject
    lateinit var quotesHistoryPresenterFactory: QuotesHistoryPresenterFactory

    companion object {
        fun newInstance(): Fragment = QuotesHistoryFragment()
    }

    private val binding: QuotesHistoryFragmentBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val presenter by moxyPresenter { quotesHistoryPresenterFactory.create() }
    private var adapter: QuoteAdapter? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            binding.root

    //проинициализировать view
    override fun init() {
        with(binding) {
            adapter = QuoteAdapter(presenter = presenter)
            quotesRecyclerView.adapter = adapter
            quotesRecyclerView.itemAnimator = DefaultItemAnimator()
            quotesRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    //обновить список цитат
    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    //показать возникшую ошибку
    override fun showException(throwable: Throwable) {
        requireContext().showToast(throwable.message)
    }
}