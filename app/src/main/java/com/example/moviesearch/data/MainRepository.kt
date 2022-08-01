package com.example.moviesearch.data

import com.example.moviesearch.data.dataAccessObject.FilmDataAccessObject
import com.example.moviesearch.data.entity.Film
import java.util.concurrent.Executors

class MainRepository(private val filmDataAccessObject: FilmDataAccessObject) {

    fun putToDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDataAccessObject.insertAll(films)
        }
    }

    fun getAllFilmsFromDb(): List<Film> {
        return filmDataAccessObject.getCashedFilms()
    }
}