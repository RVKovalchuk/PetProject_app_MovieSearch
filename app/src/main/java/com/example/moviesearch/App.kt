package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.data.api.ApiConstants
import com.example.moviesearch.data.api.TmbdApi
import com.example.moviesearch.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this

        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(AppConstants.CALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val retrofitService = retrofit.create(TmbdApi::class.java)
        interactor = Interactor(retrofitService)
    }


    companion object {
        lateinit var instance: App
            private set
    }
}