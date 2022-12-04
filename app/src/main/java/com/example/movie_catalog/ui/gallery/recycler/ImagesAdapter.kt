package com.example.movie_catalog.ui.gallery.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.ItemGalleryImageBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor(): RecyclerView.Adapter<ImageViewHolder>() {

    private var images: List<FilmImageUrlDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listImages: List<FilmImageUrlDTO>){
        images = listImages
        notifyDataSetChanged()
        Log.d("KDS", "ImagesAdapter, set new list. Size list=${images.size} ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemGalleryImageBinding.inflate( LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount()= images.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("KDS", "ImagesAdapter, onBindViewHolder start")
        val animationCard = LoadImageURLShow()
        holder.binding.image.foregroundGravity = Gravity.CENTER_HORIZONTAL
        with(holder.binding.image){
            if (position % 3 == 2) {
                layoutParams.width = resources.getDimension(R.dimen.gallery_list_big_card_width).toInt()
                layoutParams.height = resources.getDimension(R.dimen.gallery_list_big_card_height).toInt()
                holder.binding.root.gravity = Gravity.CENTER_HORIZONTAL
                animationCard.setAnimation(holder.binding.image, images[position].imageUrl!!)
            } else if (position % 3 == 1) {
                holder.binding.root.gravity = Gravity.START
                animationCard.setAnimation(holder.binding.image, images[position].previewUrl!!)
            } else if (position % 3 == 0) {
                holder.binding.root.gravity = Gravity.END
                animationCard.setAnimation(holder.binding.image, images[position].previewUrl!!)
            }
        }
    }
}

class ImageViewHolder(val binding: ItemGalleryImageBinding): RecyclerView.ViewHolder(binding.root)