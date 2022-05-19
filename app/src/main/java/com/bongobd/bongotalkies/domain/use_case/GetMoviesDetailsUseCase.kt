package com.bongobd.bongotalkies.domain.use_case

import android.util.Log
import com.bongobd.bongotalkies.common.Resource
import com.bongobd.bongotalkies.data.remote.dto.toMovie
import com.bongobd.bongotalkies.data.remote.dto.toMovieDetails
import com.bongobd.bongotalkies.domain.model.Movie
import com.bongobd.bongotalkies.domain.model.MovieDetails
import com.bongobd.bongotalkies.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetMoviesDetailsUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<MovieDetails>> = flow {

        try {
            emit(Resource.Loading())

            val response = repository.getMovieDetails().toMovieDetails()
            Log.d("TAG", "invoke: $response")
           // val data = response.toMovieDetails()

            emit(Resource.Success(response))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))

        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Check Internet Connection!!!"))
        }

    }
}