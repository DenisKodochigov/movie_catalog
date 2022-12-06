package com.example.movie_catalog.ui.kit_films.recyclerKitFilms

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemHomeFilmListBinding
import com.example.movie_catalog.entity.Film
import javax.inject.Inject

class FullListFilmAdapter @Inject constructor(
    private val onClick: (Film) -> Unit
) : RecyclerView.Adapter<FullListFilmViewHolder>() {

    private var listFilm: List<Film> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listFilm: List<Film>) {
        this.listFilm = listFilm
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullListFilmViewHolder {
        return FullListFilmViewHolder(
            ItemHomeFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FullListFilmViewHolder, position: Int) {
        var genreTxt = ""
        val film = listFilm.getOrNull(position)!!
//Set film name
        holder.binding.nameFilm.text = film.nameRu ?: ""
//Set film genres.
        film.genres.forEach {
            genreTxt = if (genreTxt == "") {
                it.genre.toString()
            } else {
                genreTxt + ", " + it.genre.toString()
            }
        }
        holder.binding.genreFilm.text = genreTxt
//Set viewed flag
        if (film!!.viewed) holder.binding.ivViewed.visibility= View.VISIBLE
//Set rating
        if (film.rating != null) holder.binding.tvRating.text = film.rating.toString()
        else holder.binding.tvRating.visibility = View.INVISIBLE
//Load small poster. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.poster, film.posterUrlPreview,
                                                                  R.dimen.card_film_radius)
//Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick(film)
        }
    }

    override fun getItemCount() = listFilm.size
}

class FullListFilmViewHolder(val binding: ItemHomeFilmListBinding) :
    RecyclerView.ViewHolder(binding.root)
