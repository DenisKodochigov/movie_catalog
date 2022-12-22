package com.example.movie_catalog.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBinding
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import javax.inject.Inject

class ListFilmPagingAdapter @Inject constructor(private val onClick: (Film) -> Unit
) : PagingDataAdapter<Linker, PagingViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(ItemListFilmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        if (position > Constants.HOME_QTY_FILMCARD-1){
            holder.binding.inclFilm.root.visibility = View.INVISIBLE
            holder.binding.inclShowAll.root.visibility = View.VISIBLE
        }else {
            holder.binding.inclFilm.root.visibility = View.VISIBLE
            holder.binding.inclShowAll.root.visibility = View.INVISIBLE
            val film = getItem(position)?.film
            //Set film name
            film?.let {
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
            animationCard.setAnimation(
                holder.binding.inclFilm.poster,
                film?.posterUrlPreview,
                R.dimen.card_film_radius
            )
        }
    }
}

class PagingViewHolder(val binding: ItemListFilmBinding): RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Linker>() {
    override fun areItemsTheSame(oldItem: Linker, newItem: Linker): Boolean =
                                               oldItem.film?.filmId == newItem.film?.filmId
    override fun areContentsTheSame(oldItem: Linker, newItem: Linker): Boolean = oldItem == newItem
}