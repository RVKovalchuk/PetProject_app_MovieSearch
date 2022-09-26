package com.example.core_impl.web.retrofit

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TmbdApi {

    @GET("movie/popular")
    fun getFilmsInfo(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<TmbdResultsDto>

    @GET("search/movie")
    fun getFilmsFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<TmbdResultsDto>
}