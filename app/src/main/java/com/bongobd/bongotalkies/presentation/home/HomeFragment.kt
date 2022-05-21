package com.bongobd.bongotalkies.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bongobd.bongotalkies.databinding.HomeFragmentBinding
import com.bongobd.bongotalkies.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private var movieList = arrayListOf<Movie>()
    private val viewModel:HomeViewModel by viewModels()
    private val movieAdapter by lazy {
        MovieAdapter(
            movieList
        ) {
            openMovieDetails(it)
        }
    }

    var page:Int =1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTopMovieList(page)
        initObserver()
        initViews()

    }

    private fun initViews() {
        ///set recyclerView
        binding.trendingRV.apply {
            binding.trendingRV.layoutManager = GridLayoutManager(
                requireContext(), 2,
                GridLayoutManager.VERTICAL, false
            )
            binding.trendingRV.adapter = movieAdapter
        }

        binding.trendingRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    // Load more results here
                    viewModel.getTopMovieList(page++)
                    initObserver()
                }
            }
        })


    }

    private fun initObserver() {
        ///observe Movie List form viewModel
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.topMovieList.collect { it ->
                if (it.isLoading){

                    Log.d("TAG", "onViewCreated: Loading....")
                }
                if (it.error.isNotBlank()){
                    Log.d("TAG", "onViewCreated: Error !!!")
                }

                it.list?.let {data->
                    movieList.clear()
                    movieList.addAll(data)
                    Log.d("TAG", "onViewCreated: Success ${movieList.size}")
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    private fun openMovieDetails(it: Movie) {
        val action= HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it.id)
        findNavController().navigate(action)

    }

    override fun onResume() {
        super.onResume()
        movieAdapter.updateAdapter(movieList)

    }
}