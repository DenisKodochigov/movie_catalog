package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBinding
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Film
import javax.inject.Inject

class ListFilmAdapter @Inject constructor(
    private val quantityItem: Int = 0,
    private val mode: String = Constants.FILM,
    private val onClick: (film: Film) -> Unit
): RecyclerView.Adapter<ListViewHolder>() {

    private var linkers: List<Linker> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listLinker: List<Linker>) {
        linkers = listLinker
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemListFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var film = linkers.getOrNull(position)?.film
        if (mode == Constants.SIMILAR) film = linkers.getOrNull(position)?.similarFilm
        film?.let {
            //Set film name
            holder.binding.nameFilm.text = film.nameRu ?: ""
//Set film genres.
            holder.binding.genreFilm.text = film.genresTxt()
//Set viewed flag
            if (film.viewed) holder.binding.ivViewed.visibility = View.VISIBLE
//Set rating
            if (film.rating != null) holder.binding.tvRating.text = film.rating.toString()
            else holder.binding.tvRating.visibility = View.INVISIBLE
//Load small poster. Before load image, show waiting animation.
            val animationCard = LoadImageURLShow()
            animationCard.setAnimation(holder.binding.poster, film.posterUrlPreview, R.dimen.card_film_radius)
//Set action on click item recyclerView
            holder.binding.root.setOnClickListener {
                onClick(film)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (quantityItem > 0 && linkers.size > quantityItem - 1) {
            quantityItem
        } else {
            linkers.size
        }
    }
}

class ListViewHolder(val binding: ItemListFilmBinding): RecyclerView.ViewHolder(binding.root)
