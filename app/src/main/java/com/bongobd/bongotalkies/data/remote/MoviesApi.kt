package com.bongobd.bongotalkies.data.remote

import com.bongobd.bongotalkies.data.remote.dto.MovieDetailsDTO
import com.bongobd.bongotalkies.data.remote.dto.MoviesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key") api_key:String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesDTO

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language: String,
    ):MovieDetailsDTO
}