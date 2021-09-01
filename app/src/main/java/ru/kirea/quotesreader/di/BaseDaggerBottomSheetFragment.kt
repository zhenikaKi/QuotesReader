package ru.kirea.quotesreader.helpers.di

import android.os.Bundle
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import moxy.MvpBottomSheetDialogFragment
import javax.inject.Inject

//реализация fragment так, чтобы можно было использовать с dagger и moxy
abstract class BaseDaggerBottomSheetFragment():
        MvpBottomSheetDialogFragment(),
        HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}