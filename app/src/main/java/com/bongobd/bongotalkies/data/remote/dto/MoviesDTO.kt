package com.bongobd.bongotalkies.data.remote.dto

data class MoviesDTO(
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)

