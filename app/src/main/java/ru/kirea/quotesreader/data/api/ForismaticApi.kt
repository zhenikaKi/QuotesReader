package ru.kirea.quotesreader.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.kirea.quotesreader.data.entities.Quote

interface ForismaticApi {

    //Получить цитату с сайта
    @POST("1.0/")
    @FormUrlEncoded
    fun getQuote(
        @Field("method") method: String = "getQuote",
        @Field("format") format: String = "json",
        @Field("lang") lang: String = "ru"
    ): Single<Quote>
}