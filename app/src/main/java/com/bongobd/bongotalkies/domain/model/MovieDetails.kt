package com.bongobd.bongotalkies.domain.model

import com.bongobd.bongotalkies.data.remote.dto.*

data class MovieDetails(
    val backdrop_path: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val genres: List<Genre>?,
    val spoken_languages: List<SpokenLanguage>,
    val tagline: String,
    val title: String
)