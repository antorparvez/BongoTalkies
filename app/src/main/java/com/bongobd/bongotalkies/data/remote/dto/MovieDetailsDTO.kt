package com.bongobd.bongotalkies.data.remote.dto

import com.bongobd.bongotalkies.domain.model.MovieDetails

data class MovieDetailsDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailsDTO.toMovieDetails() : MovieDetails{
    return MovieDetails(
        adult= adult,
        backdrop_path=backdrop_path,
        belongs_to_collection= belongs_to_collection,
        budget=budget,
        genres=this.genres,
        homepage=homepage,
        id=id,
        imdb_id=imdb_id,
        original_language=original_language,
        original_title=original_title,
        overview=overview,
        popularity = popularity,
        poster_path = poster_path,
        production_companies = production_companies,
        production_countries = production_countries,
        release_date = this.release_date,
        revenue = revenue,
        runtime = runtime,
        spoken_languages = spoken_languages,
        status = status,
        tagline=tagline,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )
}