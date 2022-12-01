package com.example.movie_catalog.ui.list_film.recyclerListFilms

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

class ListFilmAdapter @Inject constructor(
    private val onClick: (Film) -> Unit
) : RecyclerView.Adapter<ListPersonViewHolder>() {

    private var listFilm: List<Film> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listFilm: List<Film>) {
        this.listFilm = listFilm
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPersonViewHolder {
        return ListPersonViewHolder(
            ItemHomeFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListPersonViewHolder, position: Int) {
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
        if (film.viewed) holder.binding.ivViewed.visibility= View.VISIBLE
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

    override fun getItemCount() = listFilm.size
}

class ListFilmViewHolder(val binding: ItemHomeFilmListBinding) :
    RecyclerView.ViewHolder(binding.root)
