package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        var genreTxt = ""
        val film = films.getOrNull(position)
//Set film name
        holder.binding.nameFilm.text = film?.nameRu ?: ""
//Set film genres.
        film?.genres?.forEach {
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
        val animationCard = holder.binding.poster.background as AnimationDrawable

        if (film.posterUrlPreview == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        } else {
            film.let {
                Glide.with(holder.binding.poster).load(it.posterUrlPreview)
                    .into(holder.binding.poster)
                animationCard.stop()
            }
        }

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
