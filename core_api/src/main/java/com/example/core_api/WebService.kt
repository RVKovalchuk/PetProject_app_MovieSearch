package com.example.core_api

import com.example.domain.Film
import io.reactivex.rxjava3.core.Single

interface WebService {
    fun getFilmsInfo(apiKey: String, language: String, page: Int): Single<out List<Film>>

    fun getFilmsFromSearch(
        apiKey: String,
        language: String,
        query: String,
        page: Int
    ): Single<out List<Film>>
}