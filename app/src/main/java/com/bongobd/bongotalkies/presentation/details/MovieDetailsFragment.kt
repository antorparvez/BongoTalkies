package com.bongobd.bongotalkies.presentation.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.bongobd.bongotalkies.R
import com.bongobd.bongotalkies.databinding.HomeFragmentBinding
import com.bongobd.bongotalkies.databinding.MovieDetailsFragmentBinding
import com.bongobd.bongotalkies.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding:MovieDetailsFragmentBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MovieDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetails()



        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.movieDetails.collect { it ->
                if (it.isLoading){

                    Log.d("TAG", "onViewCreated: Loading....")
                }
                if (it.error.isNotBlank()){
                    Log.d("TAG", "onViewCreated: Error !!!")
                }

                it.list?.let {data->

                    Log.d("TAG", "onViewCreated: Success $data")
                }
            }
        }
    }
}