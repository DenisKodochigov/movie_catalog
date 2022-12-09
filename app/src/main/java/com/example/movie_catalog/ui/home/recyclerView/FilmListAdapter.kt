package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemHomeFilmListBinding
import com.example.movie_catalog.entity.Film
import javax.inject.Inject

class FilmListAdapter @Inject constructor(
    private val quantityItem:Int,
    private val onClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmViewHolder>() {

    private var films: List<Film> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listFilm: List<Film>) {
        this.films = listFilm
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            ItemHomeFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films.getOrNull(position)
//Set film name
        holder.binding.nameFilm.text = film?.nameRu ?: ""
//Set film genres.
        holder.binding.genreFilm.text = film?.genresTxt()
//Set viewed flag
        if (film!!.viewed) holder.binding.ivViewed.visibility= View.VISIBLE
//Set rating
        if (film.rating != null) holder.binding.tvRating.text = film.rating.toString()
        else holder.binding.tvRating.visibility = View.INVISIBLE
//Load small poster. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()//holder.binding.poster.background as AnimationDrawable
        animationCard.setAnimation(holder.binding.poster,film.posterUrlPreview,R.dimen.card_film_radius)

//Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick(film)
        }
    }

    override fun getItemCount(): Int {
        return if (films.size > quantityItem - 1) {
            quantityItem
        } else {
            films.size
        }
    }
}

class FilmViewHolder(val binding: ItemHomeFilmListBinding) :
    RecyclerView.ViewHolder(binding.root)
