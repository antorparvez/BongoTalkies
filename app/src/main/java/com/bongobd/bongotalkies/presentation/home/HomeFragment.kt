package com.bongobd.bongotalkies.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.bongobd.bongotalkies.databinding.HomeFragmentBinding
import com.bongobd.bongotalkies.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private var movieList = arrayListOf<Movie>()
    private val viewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMealSearchList()



        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.topMovieList.collect { it ->
                if (it.isLoading){

                    Log.d("TAG", "onViewCreated: Loading....")
                }
                if (it.error.isNotBlank()){
                    Log.d("TAG", "onViewCreated: Error !!!")
                }

                it.list?.let {data->
                    Log.d("TAG", "onViewCreated: Success")
                    movieList.addAll(data)
                    Log.d("TAG", "onViewCreated: Success ${movieList.size}")
                }
            }
        }
    }
}