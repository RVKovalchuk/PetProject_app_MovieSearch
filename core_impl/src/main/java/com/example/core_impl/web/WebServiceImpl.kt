package com.example.core_impl.web

import android.util.Log
import com.example.core_api.WebService
import com.example.core_impl.web.retrofit.TmbdApi
import com.example.domain.ApiConstants
import com.example.domain.Film
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WebServiceImpl(private val api: TmbdApi): WebService {

    override fun getFilmsInfo(
        apiKey: String,
        language: String,
        page: Int
    ): Single<out List<Film>> {
        return api.getFilmsInfo(ApiConstants.API_KEY, "ru-RU", page)
            .map {
                Log.d("!!!", "${it.results}")
                it.results
            }
    }

    override fun getFilmsFromSearch(
        apiKey: String,
        language: String,
        query: String,
        page: Int
    ): Single<List<Film>> {
        return api.getFilmsFromSearch(ApiConstants.API_KEY, "ru-RU", query, 1)
            .map{
                it.results
            }
    }
}