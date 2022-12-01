package com.example.movie_catalog.ui.kit_films.recyclerKitFilms

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.ItemHomeFilmListBinding
import com.example.movie_catalog.entity.Film
import javax.inject.Inject

class FullListFilmPagingAdapter @Inject constructor(
    private val onClick: (Film) -> Unit
) : PagingDataAdapter<Film, FullListFilmPagingViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FullListFilmPagingViewHolder {
        return FullListFilmPagingViewHolder(
            ItemHomeFilmListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FullListFilmPagingViewHolder, position: Int) {
        var genreTxt = ""
        val film = getItem(position)
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

        //Load small poster. Before load image, show waiting animation.
        val animationCard = holder.binding.poster.background as AnimationDrawable
        if (film?.posterUrlPreview == null) {
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
            film?.let {
                onClick(film)
            }
        }
    }
}

class FullListFilmPagingViewHolder(val binding: ItemHomeFilmListBinding) :
    RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
}