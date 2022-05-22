package com.bongobd.bongotalkies.presentation.details

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bongobd.bongotalkies.R
import com.bongobd.bongotalkies.data.remote.dto.Genre


class GenresAdapter(private val list: List<Genre>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val img = ImageView(parent?.context)
        setImage(getItem(position),img)
        img.scaleType = ImageView.ScaleType.FIT_XY
        img.layoutParams = ViewGroup.LayoutParams(300,300)

        return img
    }

    private fun setImage(itemID: Int, img: ImageView) {
        var image = 0
        when(itemID) {
            28 -> image = R.drawable.action
            12 -> image =R.drawable.advanture
            16 -> image =R.drawable.animation
            35 -> image =R.drawable.comedy
            80 -> image =R.drawable.crime
            99 -> image =R.drawable.documentry
            18 -> image =R.drawable.drama
            10751 -> image =R.drawable.family
            14 -> image =R.drawable.fantasy
            36 -> image =R.drawable.history
            27 -> image =R.drawable.horror
            10402 -> image =R.drawable.music
            9648 -> image =R.drawable.mystry
            10749 -> image =R.drawable.romance
            878 -> image =R.drawable.scifi
            10770 -> image =R.drawable.tv
            53 -> image =R.drawable.thriller
            10752 -> image =R.drawable.war
            37 -> image =R.drawable.western
        }

        img.setImageResource(image)
    }

    override fun getItem(position: Int): Int = list[position].id

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = list.size
}