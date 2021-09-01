package ru.kirea.quotesreader.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.entities.Setting

@Dao
interface SettingDao {

    //обновить настройку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(setting: Setting): Completable

    ///получить настройку
    @Query("select * from ${StorageDB.TABLE_SETTING} where ${StorageDB.SETTING_NAME} = :name")
    fun getSetting(name: String): Single<Setting>

    ///получить настройки
    @Query("select * from ${StorageDB.TABLE_SETTING}")
    fun getSettings(): Single<List<Setting>>
}