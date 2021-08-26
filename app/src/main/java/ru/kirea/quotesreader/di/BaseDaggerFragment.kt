package ru.kirea.quotesreader.helpers.di

import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment
import javax.inject.Inject

//реализация fragment так, чтобы можно было использовать с dagger и moxy
abstract class BaseDaggerFragment(@LayoutRes contentLayoutId: Int = 0):
    MvpAppCompatFragment(contentLayoutId),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}