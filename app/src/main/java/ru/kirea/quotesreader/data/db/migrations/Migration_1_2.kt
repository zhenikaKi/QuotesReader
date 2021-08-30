package ru.kirea.quotesreader.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.kirea.quotesreader.data.db.StorageDB

object Migration_1_2: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //создаем новую таблицу
        database.execSQL("create table ${StorageDB.TABLE_SETTING} (" +
                "${StorageDB.SETTING_NAME} text primary key not null, " +
                "${StorageDB.SETTING_VALUE} text not null" +
                ");"
        )

        //создаем индекс
        database.execSQL("create index index_${StorageDB.TABLE_SETTING}_${StorageDB.SETTING_NAME} " +
                "on ${StorageDB.TABLE_SETTING} (${StorageDB.SETTING_NAME});"
        )
    }
}