package com.example.moviesearch.domain

import com.example.core_api.WebService
import com.example.moviesearch.data.MainRepository
import com.example.domain.ApiConstants.API_KEY
import com.example.domain.Film
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class Interactor(
    private val repository: MainRepository,
    private val service: WebService
) {

    fun getFilmsFromApi(page: Int) {
        service.getFilmsInfo(API_KEY, "ru-RU", page)
            .map {
                repository.putToDb(it)
            }
            .subscribeOn(Schedulers.io())
            .subscribe({}, {})
    }

    fun getFilmsFromDB(): Observable<List<Film>> = repository.getAllFilmsFromDb()

    fun getWatchLaterFilmsFromDb() : Observable<List<Film>> = repository.getWatchLaterFilms()

    fun getSearchResultFromApi(search: String): Single<List<Film>> =
        service.getFilmsFromSearch(API_KEY, "ru-RU", search, 1)
            .map {
                repository.putToDb(it)
                it
            }
}