package ru.kirea.quotesreader.basic.quote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class QuoteScreen: FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = QuoteFragment.newInstance()
}