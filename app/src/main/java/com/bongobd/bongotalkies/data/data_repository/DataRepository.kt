package com.bongobd.bongotalkies.data.data_repository


import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.data.remote.MoviesApi
import com.bongobd.bongotalkies.data.remote.dto.MovieDetailsDTO
import com.bongobd.bongotalkies.data.remote.dto.MoviesDTO
import com.bongobd.bongotalkies.domain.repository.ApiRepository

class DataRepository(private val api: MoviesApi) : ApiRepository {
    override suspend fun getTopMovies(page: Int): MoviesDTO {
        return  api.getTopMovies(Constants.BASE_API_KEY,Constants.BASE_LANGUAGE,page)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO {
        return api.getMovieDetails(movieId,Constants.BASE_API_KEY,Constants.BASE_LANGUAGE)

    }


}