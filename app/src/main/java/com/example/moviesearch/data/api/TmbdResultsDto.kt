package com.example.moviesearch.data.api

data class TmbdResultsDto(
    val page: Int,
    val tmbdFilmCards: List<TmbdFilmCard>,
    val total_pages: Int,
    val total_results: Int
)