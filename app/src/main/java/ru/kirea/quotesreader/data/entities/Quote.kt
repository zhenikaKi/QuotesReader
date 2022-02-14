package ru.kirea.quotesreader.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.kirea.quotesreader.data.db.StorageDB

@Entity(
    tableName = StorageDB.TABLE_QUOTE,
    indices = [
        Index(StorageDB.QUOTE_ID),
        Index(StorageDB.QUOTE_TEXT)
    ]
)
data class Quote(

    @ColumnInfo(name = StorageDB.QUOTE_ID)
    @PrimaryKey(autoGenerate = true)
    var quoteId: Long? = null,

    @SerializedName("quoteText")
    @ColumnInfo(name = StorageDB.QUOTE_TEXT)
    val quoteText: String,

    @SerializedName("quoteAuthor")
    @ColumnInfo(name = StorageDB.QUOTE_AUTHOR)
    val quoteAuthor: String,

    @SerializedName("quoteLink")
    @ColumnInfo(name = StorageDB.QUOTE_LINK)
    val quoteLink: String? = null,

    @ColumnInfo(name = StorageDB.VIEWED)
    var viewed: Long = System.currentTimeMillis()
)