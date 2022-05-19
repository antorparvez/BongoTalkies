package com.bongobd.bongotalkies.domain.use_case

import com.bongobd.bongotalkies.common.Resource
import com.bongobd.bongotalkies.data.remote.dto.toMovie
import com.bongobd.bongotalkies.domain.model.Movie
import com.bongobd.bongotalkies.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetTopMoviesUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {

        try {
            emit(Resource.Loading())

            val response = repository.getTopMovies()
            val list = if (response.results.isNullOrEmpty()) emptyList<Movie>() else response.results.map {
                it.toMovie()
            }
            emit(Resource.Success(data = list))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))

        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Check Internet Connection!!!"))
        }

    }
}