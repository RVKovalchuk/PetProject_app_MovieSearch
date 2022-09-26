package com.example.core_impl.web.retrofit

import com.example.core_impl.FilmImpl

data class TmbdResultsDto(
    val page: Int,
    val results: List<FilmImpl>,
    val total_pages: Int,
    val total_results: Int
)