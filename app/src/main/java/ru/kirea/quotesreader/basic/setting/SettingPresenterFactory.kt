package ru.kirea.quotesreader.basic.setting

import dagger.assisted.AssistedFactory

@AssistedFactory
interface SettingPresenterFactory {
    fun create(): SettingPresenter
}