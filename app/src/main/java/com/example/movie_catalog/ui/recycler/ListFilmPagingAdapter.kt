package com.example.movie_catalog.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBinding
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
        val film = getItem(position)?.film
        //Set film name
        film?.let{
            holder.binding.nameFilm.text = film.nameRu ?: ""
            //Set film genres.
            holder.binding.genreFilm.text = film.genresTxt()
            //Load small poster. Before load image, show waiting animation.
            val animationCard = LoadImageURLShow()
            animationCard.setAnimation(holder.binding.poster, film.posterUrlPreview, R.dimen.card_film_radius )
            //Set action on click item recyclerView
            holder.binding.root.setOnClickListener { onClick(film) }
        }
    }
}

class PagingViewHolder(val binding: ItemListFilmBinding): RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Linker>() {
    override fun areItemsTheSame(oldItem: Linker, newItem: Linker): Boolean =
                                               oldItem.film?.filmId == newItem.film?.filmId
    override fun areContentsTheSame(oldItem: Linker, newItem: Linker): Boolean = oldItem == newItem
}