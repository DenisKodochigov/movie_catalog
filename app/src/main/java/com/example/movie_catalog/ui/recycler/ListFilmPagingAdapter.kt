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
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.ModeViewer
import javax.inject.Inject

class ListFilmPagingAdapter @Inject constructor(private val mode: ModeViewer,
                                                private val onClick: (Film) -> Unit
) : PagingDataAdapter<Linker, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_list_film_bot -> PagingBotVH(
                ItemListFilmBotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_list_film_right -> PagingRightVH(
                ItemListFilmRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (mode) {
            ModeViewer.FILM -> R.layout.item_list_film_bot
            ModeViewer.SIMILAR -> R.layout.item_list_film_bot
            ModeViewer.FILMOGRAPHY -> R.layout.item_list_film_right
            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d("KDS", "PagingDataAdapter, position=$position")
        val filmF = getItem(position)?.film
        when (holder) {
            is PagingBotVH -> {
                if (position > Constants.HOME_QTY_FILMCARD-1){
                    holder.binding.inclFilm.root.visibility = View.INVISIBLE
                    holder.binding.inclShowAll.root.visibility = View.VISIBLE
                }else {
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
            }
            is PagingRightVH -> {
//        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")
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
                        startYear.text = film.year.toString().orEmpty() + " " + film.genresTxt()
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