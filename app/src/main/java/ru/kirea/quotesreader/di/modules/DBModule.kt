package ru.kirea.quotesreader.di.modules

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import ru.kirea.quotesreader.data.db.StorageDB
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DBModule {

    @Singleton
    @Provides
    fun provideStorageDB(context: Context): StorageDB =
        Room.databaseBuilder(context.applicationContext, StorageDB::class.java, StorageDB.DB_NAME)
            .setQueryCallback({ sql, param ->
                Log.d("DBSql", "SQL: $sql, param: $param")
            }, Executors.newSingleThreadExecutor())
            .build()

}