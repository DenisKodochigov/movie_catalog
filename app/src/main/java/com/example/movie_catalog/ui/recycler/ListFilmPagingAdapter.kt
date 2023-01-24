package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBotBinding
import com.example.movie_catalog.databinding.ItemListFilmRightBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.ModeViewer
import javax.inject.Inject

/*
The adapter is used in:
 1. The Kit Fragment to display a list premieres
 2. The Search Fragment to display a list movies

To properly connect the necessary modules, the ModeViewer enumeration is used

 */
class ListFilmPagingAdapter @Inject constructor(private val mode: ModeViewer, //For what context is the adapter called
                                                private val onClick: (Film) -> Unit //Call back on click item recyclerview
) : PagingDataAdapter<Linker, RecyclerView.ViewHolder>(DiffUtilCallback()) {
    //Assign the holder depending on the adapter's operating mode
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_list_film_bot -> PagingBotVH(
                ItemListFilmBotBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false))
            R.layout.item_list_film_right -> PagingRightVH(
                ItemListFilmRightBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false))
            // in case populated with a model we don't know how to display.
            else -> throw IllegalArgumentException("Unsupported layout")
        }
    }
    //We connect the markup depending on the mode viewer
    override fun getItemViewType(position: Int): Int {
        return when (mode) {
            ModeViewer.FILM -> R.layout.item_list_film_bot
            ModeViewer.SEARCH -> R.layout.item_list_film_right
            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val filmF = getItem(position)?.film
        when (holder) {
            is PagingBotVH -> {
                holder.binding.inclClear.root.visibility = View.INVISIBLE
                holder.binding.inclFilm.root.visibility = View.VISIBLE
                holder.binding.inclShowAll.root.visibility = View.INVISIBLE
                //Set film name
                filmF?.let { film ->
                    holder.binding.inclFilm.nameFilm.text = film.nameRu ?: ""
                    //Set film genres.
                    holder.binding.inclFilm.genreFilm.text = film.genresTxt()
                    //Set viewed flag
                    if (film.viewed) {
                        holder.binding.inclFilm.ivViewed.visibility = View.VISIBLE
                        holder.binding.root.background = App.context.getDrawable(R.drawable.gradientviewed)
                    }
                    //Set action on click item recyclerView
                    holder.binding.root.setOnClickListener { onClick(film) }
                }
                //Load small poster. Before load image, show waiting animation.
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.inclFilm.poster, filmF?.posterUrlPreview, R.dimen.card_film_radius)
            }
            is PagingRightVH -> {
                val animationCard = LoadImageURLShow()
                filmF?.let { film ->
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
                        nameFilm.text = film.nameRu ?: film.nameEn ?: film.nameOriginal
                        //Set film date
                        startYear.text = film.year.toString() + " " + film.genresTxt()
                        //Set action on click item recyclerView
                        poster.setOnClickListener {
                            onClick(film)
                        }
                    }
                }
            }
        }
    }
}

class PagingBotVH(val binding: ItemListFilmBotBinding): RecyclerView.ViewHolder(binding.root)
class PagingRightVH(val binding: ItemListFilmRightBinding): RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Linker>() {
    override fun areItemsTheSame(oldItem: Linker, newItem: Linker): Boolean =
                                               oldItem.film?.filmId == newItem.film?.filmId
    override fun areContentsTheSame(oldItem: Linker, newItem: Linker): Boolean = oldItem == newItem
}