package com.bongobd.bongotalkies.data.remote

import com.bongobd.bongotalkies.data.remote.dto.MovieDetailsDTO
import com.bongobd.bongotalkies.data.remote.dto.MoviesDTO
import retrofit2.http.GET

interface MoviesApi {
    @GET("top_rated?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US&p%20age=1")
    suspend fun getTopMovies(): MoviesDTO

    @GET("122?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US")
    suspend fun getMovieDetails():MovieDetailsDTO
}