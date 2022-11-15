package com.example.core_api

import com.example.domain.Film
import io.reactivex.rxjava3.core.Observable

//Интерфейс с вынесенными методами работы с Room DB
interface DbService {
    fun getCashedFilms(): Observable<List<Film>>

    fun getWatchLaterFilms() : Observable<List<Film>>

    fun insertAll(list: List<Film>)

    fun insert(film: Film)
}