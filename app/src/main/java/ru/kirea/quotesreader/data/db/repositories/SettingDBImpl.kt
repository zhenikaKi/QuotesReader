package ru.kirea.quotesreader.data.db.repositories

import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.entities.Setting
import javax.inject.Inject

class SettingDBImpl @Inject constructor(private val storageDB: StorageDB): SettingDB {
    override fun save(setting: Setting) = storageDB.settingDao().insert(setting)

    override fun getSetting(name: String) = storageDB.settingDao().getSetting(name)

    override fun getSettings() = storageDB.settingDao().getSettings()
}