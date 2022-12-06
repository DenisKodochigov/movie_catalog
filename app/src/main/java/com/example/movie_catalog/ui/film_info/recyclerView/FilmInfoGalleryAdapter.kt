package com.example.movie_catalog.ui.film_info.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.ItemFilmInfoGalleryBinding
import com.example.movie_catalog.entity.filminfo.Gallery
import javax.inject.Inject

class FilmInfoGalleryAdapter @Inject constructor(private val onClick: (Gallery) -> Unit):
    RecyclerView.Adapter<FilmInfoGalleryViewHolder>() {

    private var listImage: List<FilmImageUrlDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(imageList:List<FilmImageUrlDTO>){
        listImage = imageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmInfoGalleryViewHolder {
        return FilmInfoGalleryViewHolder(ItemFilmInfoGalleryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FilmInfoGalleryViewHolder, position: Int) {
        val imageFilm = listImage[position]

        //Load small poster. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.poster,imageFilm.previewUrl,
                                R.dimen.film_info_gallery_radius)

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            val imageBandle = Gallery()
            onClick(imageBandle)
        }
    }

    override fun getItemCount(): Int {
        return if (listImage.size > Constants.HOME_QTY_FILMCARD - 1) Constants.HOME_QTY_FILMCARD
                else listImage.size
    }
}

class FilmInfoGalleryViewHolder(
    val binding: ItemFilmInfoGalleryBinding): RecyclerView.ViewHolder(binding.root)