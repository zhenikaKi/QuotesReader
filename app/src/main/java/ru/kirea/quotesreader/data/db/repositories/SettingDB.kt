package ru.kirea.quotesreader.data.db.repositories

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.entities.Setting

interface SettingDB {

    //обновить настройку
    fun save(setting: Setting): Completable

    //получить настройку
    fun getSetting(name: String): Single<Setting>

    //получить настройки
    fun getSettings(): Single<List<Setting>>
}