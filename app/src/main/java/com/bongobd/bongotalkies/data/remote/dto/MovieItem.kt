package com.bongobd.bongotalkies.data.remote.dto

import com.bongobd.bongotalkies.domain.model.Movie

data class MovieItem(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieItem.toMovie(): Movie {
    return Movie(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        id = this.id,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        vote_average = this.vote_average,
        vote_count = this.vote_count

    )
}