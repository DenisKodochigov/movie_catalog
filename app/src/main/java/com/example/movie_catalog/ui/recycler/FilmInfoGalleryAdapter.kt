package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemFilmPageGalleryBinding
import com.example.movie_catalog.entity.filminfo.ImageFilm
import javax.inject.Inject

class FilmInfoGalleryAdapter @Inject constructor(private val onClick: () -> Unit):
    RecyclerView.Adapter<FilmInfoGalleryViewHolder>() {

    private var listImage: List<ImageFilm> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(imageList:List<ImageFilm>){
        listImage = imageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmInfoGalleryViewHolder {
        return FilmInfoGalleryViewHolder(ItemFilmPageGalleryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FilmInfoGalleryViewHolder, position: Int) {
        val imageFilm = listImage[position]

        //Load small poster. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.poster,imageFilm.previewUrl, R.dimen.film_page_gallery_radius)

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick()
        }
    }

    override fun getItemCount(): Int {
        return if (listImage.size > Constants.HOME_QTY_FILMCARD - 1) Constants.HOME_QTY_FILMCARD
                else listImage.size
    }
}

class FilmInfoGalleryViewHolder(val binding: ItemFilmPageGalleryBinding):
    RecyclerView.ViewHolder(binding.root)