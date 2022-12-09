package com.example.movie_catalog.ui.gallery.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.ItemGalleryViewerRecyclerBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor(private val onClick: (String) -> Unit ) : RecyclerView.Adapter<ImageViewHolder>() {

    private var images: List<FilmImageUrlDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listImages: List<FilmImageUrlDTO>) {
        images = listImages
        notifyDataSetChanged()
//        Log.d("KDS", "ImagesAdapter, set new list. Size list=${images.size} ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemGalleryViewerRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = images.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")
        val animationCard = LoadImageURLShow()
        holder.binding.image.foregroundGravity = Gravity.CENTER_HORIZONTAL
        with(holder.binding) {
            if (position % 3 == 2) {
                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_big_card_width).toInt()
                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_big_card_height).toInt()
                root.gravity = Gravity.CENTER_HORIZONTAL
                animationCard.setAnimation( image, images[position].imageUrl!!, R.dimen.gallery_list_big_card_radius)
            } else if (position % 3 == 1) {
                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                root.gravity = Gravity.START
                animationCard.setAnimation(image, images[position].previewUrl!!, R.dimen.gallery_list_small_card_radius)
            } else if (position % 3 == 0) {
                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                root.gravity = Gravity.END
                animationCard.setAnimation( image, images[position].previewUrl!!, R.dimen.gallery_list_small_card_radius
                )
            }

            image.setOnClickListener {
                onClick( images[position].imageUrl!!)
            }
        }
    }
}

class ImageViewHolder(val binding: ItemGalleryViewerRecyclerBinding) : RecyclerView.ViewHolder(binding.root)