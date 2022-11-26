package com.example.movie_catalog.ui.film_info.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.Constants
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.ItemFilmInfoGalleryBinding
import javax.inject.Inject

class FilmInfoGalleryAdapter @Inject constructor(private val onClick: (FilmImageUrlDTO) -> Unit):
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
        val animationCard = holder.binding.poster.background as AnimationDrawable

        if (imageFilm.previewUrl == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        }else{
            imageFilm.let {
                Glide.with(holder.binding.poster).load(it.previewUrl).into(holder.binding.poster)
                animationCard.stop()
            }
        }

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick(imageFilm)
        }
    }

    override fun getItemCount(): Int {
        return if (listImage.size > Constants.QTY_CARD - 1) Constants.QTY_CARD
                else listImage.size
    }
}

class FilmInfoGalleryViewHolder(
    val binding: ItemFilmInfoGalleryBinding): RecyclerView.ViewHolder(binding.root)