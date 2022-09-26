package com.example.moviesearch.data

import com.example.core_api.DbService
import com.example.domain.Film
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class MainRepository(private val filmDataAccessObject: DbService) {

    fun putToDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDataAccessObject.insertAll(films)
        }
    }

    fun getAllFilmsFromDb(): Observable<List<Film>> {
        return filmDataAccessObject.getCashedFilms()
    }
}