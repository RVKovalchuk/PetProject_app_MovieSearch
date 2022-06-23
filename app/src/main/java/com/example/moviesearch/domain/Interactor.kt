package com.example.moviesearch.domain

import com.example.moviesearch.data.MainRepository

class Interactor(val repository: MainRepository) {
    fun getFilmsDB() : List<Film> = repository.filmsDataBase
}