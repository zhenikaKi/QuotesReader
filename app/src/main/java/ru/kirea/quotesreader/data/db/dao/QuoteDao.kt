package ru.kirea.quotesreader.data.db.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kirea.quotesreader.data.db.StorageDB
import ru.kirea.quotesreader.data.entities.Quote

@Dao
interface QuoteDao {

    //вставить цитату
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quote: Quote): Completable

    //получить просмотренные цитаты
    @Query("select * from ${StorageDB.TABLE_QUOTE} order by ${StorageDB.VIEWED} desc")
    fun getQuotes(): Single<List<Quote>>

    //удалить цитату
    @Delete
    fun delete(quote: Quote): Completable
}