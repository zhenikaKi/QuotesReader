package ru.kirea.quotesreader.basic.quoteshistory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class QuotesHistoryScreen: FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = QuotesHistoryFragment.newInstance()
}