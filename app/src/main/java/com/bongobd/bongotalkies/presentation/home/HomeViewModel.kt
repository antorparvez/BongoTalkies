package com.bongobd.bongotalkies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongobd.bongotalkies.common.Resource
import com.bongobd.bongotalkies.domain.model.Movie
import com.bongobd.bongotalkies.domain.use_case.GetTopMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val topMovieUseCase: GetTopMoviesUseCase
) : ViewModel(){
    private val _topMovieList = MutableStateFlow(MealSearchState())
    val topMovieList : StateFlow<MealSearchState> = _topMovieList

    fun getMealSearchList(){
        topMovieUseCase().onEach {
            when(it){
                is Resource.Loading->{
                    _topMovieList.value = MealSearchState(isLoading = false)
                }

                is Resource.Error->{
                    _topMovieList.value = MealSearchState(error = it.message.toString())

                }
                is Resource.Success->{
                    _topMovieList.value= MealSearchState(list = it.data)

                }
            }
        }.launchIn(viewModelScope)
    }
}

data class MealSearchState(
    val list : List<Movie>?=null,
    val error:String ="",
    val isLoading:Boolean=false

)