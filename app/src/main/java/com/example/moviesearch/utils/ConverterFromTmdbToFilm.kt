package com.example.moviesearch.utils

import com.example.moviesearch.data.api.TmbdFilmCard
import com.example.moviesearch.domain.Film

object ConverterFromTmdbToFilm {
    fun convertFromTmbdToFilm(list: List<TmbdFilmCard>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(
                Film(
                    title = it.title,
                    poster = it.poster_path,
                    description = it.overview,
                    isInFavorites = false,
                    rating = it.vote_average
                )
            )
        }
        return result
    }
}