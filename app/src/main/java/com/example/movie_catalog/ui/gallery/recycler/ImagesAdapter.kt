package com.example.movie_catalog.ui.gallery.recycler

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.ItemGalleryImageBinding
import javax.inject.Inject


class ImagesAdapter @Inject constructor(): RecyclerView.Adapter<ImageViewHolder>() {

    private var images: List<FilmImageUrlDTO> = emptyList()

    fun setList( listImages: List<FilmImageUrlDTO>){
        images = listImages
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder( ItemGalleryImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount()= images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        //Load small poster. Before load image, show waiting animation.
        val animationCard = holder.binding.galleryImage.background as AnimationDrawable
        if (image.imageUrl == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        } else {
            image.let {
                Glide.with(holder.binding.galleryImage).load(it.imageUrl)
                    .into(holder.binding.galleryImage)
                animationCard.stop()
            }
        }
    }
}

class ImageViewHolder(val binding: ItemGalleryImageBinding): RecyclerView.ViewHolder(binding.root)