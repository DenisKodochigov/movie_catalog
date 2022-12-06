package com.example.movie_catalog.ui.viewer_image.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.FragmentGalleryPageBinding
import com.example.movie_catalog.databinding.ItemViewerImageBinding
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.gallery.ListImages

class ViewerViewPagerAdapter(private val listImages: List<String>): RecyclerView.Adapter<PagerHV>() {

    override fun getItemCount() = listImages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHV {
        return PagerHV(
            ItemViewerImageBinding.inflate( LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerHV, position: Int) {

//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.photo, listImages[position], R.dimen.card_gallery_icon_radius)
    }
}

class PagerHV(val binding: ItemViewerImageBinding): RecyclerView.ViewHolder(binding.root)