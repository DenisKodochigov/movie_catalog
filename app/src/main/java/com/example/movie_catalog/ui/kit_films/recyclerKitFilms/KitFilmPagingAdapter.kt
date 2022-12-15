package com.example.movie_catalog.ui.kit_films.recyclerKitFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemHomeFilmListBinding
import com.example.movie_catalog.entity.Film
import javax.inject.Inject

class FullListFilmPagingAdapter @Inject constructor( private val onClick: (Film) -> Unit
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
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.poster, film?.posterUrlPreview, R.dimen.card_film_radius )

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            film?.let {
                onClick(film)
            }
        }
    }
}

class FullListFilmPagingViewHolder(val binding: ItemHomeFilmListBinding):
    RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
                                                                oldItem.filmId == newItem.filmId
    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem
}