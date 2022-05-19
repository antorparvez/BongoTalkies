package com.bongobd.bongotalkies.data.data_repository


import com.bongobd.bongotalkies.data.remote.MoviesApi
import com.bongobd.bongotalkies.data.remote.dto.MovieDetailsDTO
import com.bongobd.bongotalkies.data.remote.dto.MoviesDTO
import com.bongobd.bongotalkies.domain.repository.ApiRepository

class DataRepository(private val api: MoviesApi) : ApiRepository {
    override suspend fun getTopMovies(): MoviesDTO {
      return  api.getTopMovies()
    }

    override suspend fun getMovieDetails(): MovieDetailsDTO {
      return api.getMovieDetails()
    }

}