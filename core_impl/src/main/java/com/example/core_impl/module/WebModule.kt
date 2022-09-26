package com.example.core_impl.module

import com.example.core_api.WebService
import com.example.domain.ApiConstants
import com.example.core_impl.BuildConfig
import com.example.core_impl.web.WebServiceImpl
import com.example.core_impl.web.retrofit.TmbdApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory

@Module
class WebModule {

    @Singleton
    @Provides
    fun provideWebService(api: TmbdApi): WebService =
        WebServiceImpl(api)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(ApiConstants.CALL_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideTmbdApi(retrofit: Retrofit): TmbdApi = retrofit.create(TmbdApi::class.java)
}