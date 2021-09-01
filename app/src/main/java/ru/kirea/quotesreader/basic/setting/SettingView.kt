package ru.kirea.quotesreader.basic.setting

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import ru.kirea.quotesreader.basic.ExceptionView
import ru.kirea.quotesreader.data.entities.Setting

@SingleState
interface SettingView: MvpView, ExceptionView {

    //проинициализировать view
    fun init()

    //запустить процесс получения настроек
    fun startLoadData()

    //завершить процесс получения настроек
    fun endLoadData()

    //обработать полученные настройки
    fun showSettings(settings: List<Setting>)
}