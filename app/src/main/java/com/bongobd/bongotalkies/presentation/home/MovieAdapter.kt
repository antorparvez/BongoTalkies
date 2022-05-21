package com.bongobd.bongotalkies.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bongobd.bongotalkies.R
import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.domain.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter(
     var movieList : ArrayList<Movie>,
    val onProductDetailsItemClick: (shopDetails: Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ShopViewHolder>() {

    inner class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ImageView = itemView.findViewById(R.id.moviePoster)
    }

    fun updateAdapter(newShopList: List<Movie>){
        movieList.clear()
        movieList.addAll(newShopList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        return ShopViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_row,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(Constants.BASE_IMG_URL+""+movieList[position].poster_path)
            .centerCrop()
            .placeholder(R.drawable.bongo_icon)
            .into(holder.posterImage);

        holder.itemView.setOnClickListener {
            onProductDetailsItemClick(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}