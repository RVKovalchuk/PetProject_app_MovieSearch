package com.example.moviesearch.data.api


data class TmbdResultsDto(
    val page: Int,
    val results: List<TmbdFilmCard>,
    val total_pages: Int,
    val total_results: Int
)