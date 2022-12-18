package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemFilmographyViewerRecyclerBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import javax.inject.Inject

class FilmographyRecyclerAdapter @Inject constructor(private val onClick: (Film) -> Unit) :
    RecyclerView.Adapter<FilmographyListVH>() {

    private var linkers: List<Linker> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listLinker: List<Linker>) {
        linkers = listLinker
        notifyDataSetChanged()
//        Log.d("KDS", "ImagesAdapter, set new list. Size list=${images.size} ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyListVH {
        return FilmographyListVH( ItemFilmographyViewerRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = linkers.size

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: FilmographyListVH, position: Int) {

        val filmF = linkers[position].film
//        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")
        val animationCard = LoadImageURLShow()
        filmF?. let { film ->
            with(holder.binding) {
//Set viewed flag
                if (film.viewed) {
                    ivViewed.background = App.context.getDrawable(R.drawable.icon_viewed)
                    ivViewed.visibility = View.VISIBLE
                }else{
                    ivViewed.visibility = View.INVISIBLE
                    ivViewed.layoutParams.height = 0
                }
//Set rating
                if (film.rating != null) tvRating.text = film.rating.toString()
                else tvRating.visibility = View.INVISIBLE

                animationCard.setAnimation( poster, film.posterUrlPreview, R.dimen.gallery_list_small_card_radius)
//Set film name
                if (film.nameRu != null) nameFilm.text = film.nameRu
                else if (film.nameEn != null) nameFilm.text = film.nameEn
//Set film date
                if (film.startYear != null) startYear.text = film.startYear + " " + film.genresTxt()
//Set action on click item recyclerView
                poster.setOnClickListener {
                    onClick(film)
                }
            }
        }
    }
}

class FilmographyListVH(val binding: ItemFilmographyViewerRecyclerBinding):
    RecyclerView.ViewHolder(binding.root)