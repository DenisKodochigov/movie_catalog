package com.example.movie_catalog.ui.viewer_image.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemViewerImageRecyclerBinding
import com.example.movie_catalog.entity.filminfo.Images

class ViewerViewPagerAdapter(private val listImages: List<Images>): RecyclerView.Adapter<PagerHV>() {

    override fun getItemCount() = listImages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHV {
        return PagerHV(
            ItemViewerImageRecyclerBinding.inflate( LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerHV, position: Int) {

//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
        val animationCard = LoadImageURLShow()
//        Log.d("KDS","ViewerViewPagerAdapter.onBindViewHolder animation start ${listImages[position].imageUrl}")
        animationCard.setAnimation(holder.binding.photo, listImages[position].imageUrl,
                       R.dimen.card_gallery_icon_radius, false)
    }
}

class PagerHV(val binding: ItemViewerImageRecyclerBinding): RecyclerView.ViewHolder(binding.root)