package com.bongobd.bongotalkies.presentation.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bongobd.bongotalkies.databinding.HomeFragmentBinding
import com.bongobd.bongotalkies.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.abs


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private var movieList = arrayListOf<Movie>()
    private var movieSliderList = arrayListOf<Movie>()
    private val viewModel: HomeViewModel by viewModels()
    private val movieAdapter by lazy {
        MovieAdapter(
            movieList
        ) {
            openMovieDetails(it)
        }
    }

    private val sliderAdapter by lazy {
        SliderImageAdapter(movieSliderList) {
            openMovieDetails(it)
        }
    }

    var page: Int = 1
    var is_loading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
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

        binding.trendingRV.isNestedScrollingEnabled = false
        binding.nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                page++
                binding.progressBar.setVisibility(View.VISIBLE)
                viewModel.getTopMovieList(page)
            }
        })




        binding.homePromoView.sliderViewPager.adapter = sliderAdapter

        binding.homePromoView.sliderViewPager.clipToPadding = false
        binding.homePromoView.sliderViewPager.clipChildren = false
        binding.homePromoView.sliderViewPager.offscreenPageLimit = 3
        binding.homePromoView.sliderViewPager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.90f + r * 0.04f
        }
        binding.homePromoView.sliderViewPager.setPageTransformer(compositePageTransformer)
        var currentPage = 0
        val handler = Handler()
        val update = Runnable {
            binding.homePromoView.sliderViewPager.setCurrentItem(currentPage, true);
            currentPage++
        }
        val timer = Timer()// This will create a new Thread
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 500, 3000)


    }


    private fun initObserver() {
        ///observe Movie List form viewModel
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.topMovieList.collect { it ->
                if (it.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("TAG", "onViewCreated: Loading....")
                }
                if (it.error.isNotBlank()) {
                    Log.d("TAG", "onViewCreated: Error !!!")
                }

                it.list?.let { data ->
                    movieList.addAll(data)
                    Log.d("TAG", "onViewCreated: Success ${movieList.size}")

                    if (movieSliderList.isNullOrEmpty()) {
                        for (i in 0..4) {
                            movieSliderList.add(movieList[i])
                        }
                    }
                    sliderAdapter.notifyDataSetChanged()
                    movieAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun openMovieDetails(it: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it.id)
        findNavController().navigate(action)

    }

    override fun onResume() {
        super.onResume()
        movieAdapter.updateAdapter(movieList)

    }
}