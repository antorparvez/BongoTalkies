package com.bongobd.bongotalkies.domain.repository
import com.bongobd.bongotalkies.data.remote.dto.MovieDetailsDTO
import com.bongobd.bongotalkies.data.remote.dto.MoviesDTO

interface ApiRepository {
    suspend fun getTopMovies(page:Int): MoviesDTO
    suspend fun getMovieDetails(movieId:Int): MovieDetailsDTO

}