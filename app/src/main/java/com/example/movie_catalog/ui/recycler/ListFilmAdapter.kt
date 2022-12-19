package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBinding
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.Kit
import javax.inject.Inject

class ListFilmAdapter @Inject constructor(
    private val quantityItem: Int = 0,
    private val mode: String = Constants.FILM,
    private val onClick: (film: Film) -> Unit,
    private val onClickAll: (kit: Kit) -> Unit
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        if (quantityItem > 0 && position > Constants.HOME_QTY_FILMCARD-2){
            holder.binding.inclFilm.root.visibility = View.INVISIBLE
            holder.binding.inclShowAll.root.visibility = View.VISIBLE
            holder.binding.inclShowAll.oval.setOnClickListener {
                linkers[position].kit?.let { kit -> onClickAll(kit) }
            }
        }else{
            holder.binding.inclFilm.root.visibility = View.VISIBLE
            holder.binding.inclShowAll.root.visibility = View.INVISIBLE
            var film = linkers.getOrNull(position)?.film
            if (mode == Constants.SIMILAR) film = linkers.getOrNull(position)?.similarFilm
            film?.let {
                //Set film name
                holder.binding.inclFilm.nameFilm.text = film.nameRu ?: ""
//Set film genres.
                holder.binding.inclFilm.genreFilm.text = film.genresTxt()
//Set viewed flag
                if (film.viewed) {
                    holder.binding.inclFilm.ivViewed.visibility = View.VISIBLE
                    holder.binding.inclFilm.poster.background =
                        App.context.getDrawable(R.drawable.gradient_viewed)
                }
//Set rating
                if (film.rating != null) holder.binding.inclFilm.tvRating.text = film.rating.toString()
                else holder.binding.inclFilm.tvRating.visibility = View.INVISIBLE
//Set action on click item recyclerView
                holder.binding.root.setOnClickListener {
                    onClick(film)
                }
            }
            //Load small poster. Before load image, show waiting animation.
            val animationCard = LoadImageURLShow()
            animationCard.setAnimation(holder.binding.inclFilm.poster, film?.posterUrlPreview,
                R.dimen.card_film_radius)
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
