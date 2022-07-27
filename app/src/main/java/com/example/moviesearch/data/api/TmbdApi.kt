package com.example.moviesearch.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmbdApi {

    @GET("movie/popular")
    fun getFilmsInfo(
        /*@Path("category") category: String,*/
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<TmbdResultsDto>
}