package com.bongobd.bongotalkies.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bongobd.bongotalkies.R
import com.bongobd.bongotalkies.common.Constants
import com.bongobd.bongotalkies.domain.model.Movie
import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.KenBurnsView


class SliderImageAdapter(
  private val imageList: List<Movie>,
    val onProductDetailsItemClick: (shopDetails: Movie) -> Unit) :
    RecyclerView.Adapter<SliderImageAdapter.SliderViewHolder>() {
    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val  sliderImage: KenBurnsView = itemView.findViewById(R.id.kbvLocation)
       val  infoBtn: LinearLayout = itemView.findViewById(R.id.infoBtn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    com.bongobd.bongotalkies.R.layout.img_slider,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(Constants.BASE_IMG_URL+""+imageList[position].poster_path)
            .centerCrop()
            .placeholder(R.drawable.bongo_icon)
            .into(holder.sliderImage);

        holder.infoBtn.setOnClickListener {
            onProductDetailsItemClick(imageList[position])
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}