package com.bongobd.bongotalkies.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bongobd.bongotalkies.R
import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.databinding.MovieDetailsFragmentBinding
import com.bongobd.bongotalkies.domain.model.MovieDetails
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding:MovieDetailsFragmentBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MovieDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetails(args.movieID)

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
                    setMovieData(data)
                }
            }
        }
    }

    private fun setMovieData(movieDetails: MovieDetails) {
        Glide
            .with(requireContext())
            .load(Constants.BASE_IMG_URL+""+movieDetails.backdrop_path)
            .centerCrop()
            .placeholder(R.drawable.bongo_icon)
            .into(binding.backdrop)

        Glide
            .with(requireContext())
            .load(Constants.BASE_IMG_URL+""+movieDetails.poster_path)
            .centerCrop()
            .placeholder(R.drawable.bongo_icon)
            .into(binding.posterimage)

        binding.movieTitle.text = movieDetails.title
        binding.tagline.text = movieDetails.tagline
        binding.dateStatus.text=movieDetails.release_date
        binding.releaseDate.text= "Popularity ${movieDetails.popularity}"
        binding.ageRate.text= "Languages ${movieDetails.spoken_languages[0].english_name}"
        binding.runTime.text= convertMinutesTimeToHHMMString(movieDetails.runtime)+" hr"
        binding.movieDescription.text=movieDetails.overview

        binding.playBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Feature under development", Toast.LENGTH_SHORT).show()
        }

        binding.detailsToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    fun convertMinutesTimeToHHMMString(minutesTime: Int): String? {
        val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("HH.mm")
        df.timeZone = timeZone
        return df.format(Date(minutesTime * 60 * 1000L))
    }

}