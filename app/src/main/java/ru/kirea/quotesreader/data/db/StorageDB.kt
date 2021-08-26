package ru.kirea.quotesreader.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kirea.quotesreader.data.db.dao.QuoteDao
import ru.kirea.quotesreader.data.entities.Quote

@Database(
    entities = [Quote::class],
    version = StorageDB.DB_VERSION,
    exportSchema = true)
abstract class StorageDB: RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        //база данных
        const val DB_VERSION = 1
        const val DB_NAME = "quote.db"
        //таблицы
        const val TABLE_QUOTE = "quotes"
        //столбцы
        const val QUOTE_ID = "quote_id"
        const val QUOTE_TEXT = "quote_text"
        const val QUOTE_AUTHOR = "quote_author"
        const val QUOTE_LINK = "quote_link"
        const val VIEWED = "viewed"
    }
}