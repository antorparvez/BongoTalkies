package com.bongobd.bongotalkies.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongobd.bongotalkies.common.Resource
import com.bongobd.bongotalkies.domain.model.MovieDetails
import com.bongobd.bongotalkies.domain.use_case.GetMoviesDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel  @Inject constructor(
    private val movieDetailsUseCase: GetMoviesDetailsUseCase
) : ViewModel(){
    private val _movieDetails = MutableStateFlow(MovieDetailsState())
    val movieDetails : StateFlow<MovieDetailsState> = _movieDetails

    fun getMovieDetails(){
        movieDetailsUseCase().onEach {
            when(it){
                is Resource.Loading->{
                    _movieDetails.value = MovieDetailsState(isLoading = false)
                }

                is Resource.Error->{
                    _movieDetails.value = MovieDetailsState(error = it.message.toString())

                }
                is Resource.Success->{
                    _movieDetails.value= MovieDetailsState(list = it.data)

                }
            }
        }.launchIn(viewModelScope)
    }
}

data class MovieDetailsState(
    val list : MovieDetails? = null,
    val error:String ="",
    val isLoading:Boolean=false

)