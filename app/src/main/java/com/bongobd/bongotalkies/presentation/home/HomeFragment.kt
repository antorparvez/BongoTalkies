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
    var page: Int = 1
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
        setupHomePageImageSlider()

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
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getTopMovieList(page)
            }
        })

    }

    private fun setupHomePageImageSlider() {

        binding.sliderViewPager.adapter = sliderAdapter
        binding.sliderViewPager.clipToPadding = false
        binding.sliderViewPager.clipChildren = false
        binding.sliderViewPager.offscreenPageLimit = 3
        binding.sliderViewPager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.90f + r * 0.04f
        }
        binding.sliderViewPager.setPageTransformer(compositePageTransformer)
        var currentPage = 0
        val handler = Handler()
        val update = Runnable {
            binding.sliderViewPager.setCurrentItem(currentPage, true);
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
                    binding.mainProgress.visibility = View.GONE
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
        movieList.clear()
        viewModel.getTopMovieList(1)
        movieAdapter.updateAdapter(movieList)

    }
}