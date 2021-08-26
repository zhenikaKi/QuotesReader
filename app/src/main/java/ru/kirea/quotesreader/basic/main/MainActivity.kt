package ru.kirea.quotesreader.basic.main

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.ktx.moxyPresenter
import ru.kirea.quotesreader.R
import ru.kirea.quotesreader.basic.listeners.BackButtonListener
import ru.kirea.quotesreader.databinding.MainActivityBinding
import ru.kirea.quotesreader.helpers.di.BaseDaggerActivity
import javax.inject.Inject

class MainActivity: BaseDaggerActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var mainFactory: MainFactory

    private val binding: MainActivityBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val navigator = AppNavigator(this, R.id.container)
    private val presenter by moxyPresenter { mainFactory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backPressed()
    }
}