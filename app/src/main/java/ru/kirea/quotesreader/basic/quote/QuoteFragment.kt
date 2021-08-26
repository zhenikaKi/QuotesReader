package ru.kirea.quotesreader.basic.quote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import moxy.ktx.moxyPresenter
import ru.kirea.quotesreader.R
import ru.kirea.quotesreader.basic.listeners.BackButtonListener
import ru.kirea.quotesreader.data.entities.Quote
import ru.kirea.quotesreader.databinding.QuoteFragmentBinding
import ru.kirea.quotesreader.extensions.hide
import ru.kirea.quotesreader.extensions.show
import ru.kirea.quotesreader.extensions.showToast
import ru.kirea.quotesreader.helpers.di.BaseDaggerFragment
import javax.inject.Inject

class QuoteFragment: BaseDaggerFragment(), QuoteView, BackButtonListener {

    @Inject
    lateinit var quoteFactory: QuoteFactory

    companion object {
        fun newInstance(): Fragment = QuoteFragment()
    }

    private val binding: QuoteFragmentBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val presenter by moxyPresenter { quoteFactory.create() }
    private var visibleSubmenu = false

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            binding.root

    //проинициализировать view
    override fun init() {
        buttonListener()
    }

    override fun backPressed() = presenter.backPressed()

    //начать обновление цитаты
    override fun startUpdateQuote() {
        binding.progressBar.visibility = View.VISIBLE
    }

    //завершить обновление цитаты
    override fun endUpdateQuote() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    //показать сформированную цитату
    override fun showQuote(quote: Quote) {
        binding.quoteText.hide()
        binding.quoteAuthor.hide()

        binding.quoteText.text = quote.quoteText
        binding.quoteAuthor.text = quote.quoteAuthor

        binding.quoteText.show()
        binding.quoteAuthor.show()
    }

    //показать возникшую ошибку
    override fun showException(throwable: Throwable) {
        requireContext().showToast(throwable.message)
    }

    //обработать кнопки
    private fun buttonListener() {
        //нажатие по кнопке меню
        binding.fabMenu.setOnClickListener {
            showOrHideFabMenu()
        }

        //нажатие по кнопке открытия истории статусов
        binding.fabHistoryQuote.setOnClickListener {
            TODO()
            showOrHideFabMenu()
        }

        //нажатие по кнопке настроек
        binding.fabSetting.setOnClickListener {
            TODO()
            showOrHideFabMenu()
        }

        //нажатие по кнопке ручного обновления статуса
        binding.fabUpdateQuote.setOnClickListener {
            presenter.updateQuote()
            showOrHideFabMenu()
        }

        //подключим анимацию кнопок
        binding.fabHistoryQuote
    }

    //показать или скрыть кнопки меню по фото
    private fun showOrHideFabMenu() {
        if (visibleSubmenu) {
            val animationHide = AnimationUtils.loadAnimation(context, R.anim.fab_menu_hide)
            binding.fabHistoryQuote.startAnimation(animationHide)
            binding.fabSetting.startAnimation(animationHide)
            binding.fabUpdateQuote.startAnimation(animationHide)

            binding.fabHistoryQuote.visibility = View.GONE
            binding.fabSetting.visibility = View.GONE
            binding.fabUpdateQuote.visibility = View.GONE
        } else {
            val animationShow = AnimationUtils.loadAnimation(context, R.anim.fab_menu_show)

            binding.fabHistoryQuote.visibility = View.VISIBLE
            binding.fabSetting.visibility = View.VISIBLE
            binding.fabUpdateQuote.visibility = View.VISIBLE

            binding.fabHistoryQuote.startAnimation(animationShow)
            binding.fabSetting.startAnimation(animationShow)
            binding.fabUpdateQuote.startAnimation(animationShow)
        }
        visibleSubmenu = !visibleSubmenu
    }
}