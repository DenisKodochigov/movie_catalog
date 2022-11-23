package com.example.movie_catalog.ui.home.recyclerView

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.ItemRecyclerFilmListBinding
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import javax.inject.Inject

class TopFilmPagingAdapter @Inject constructor(
    private val onClick: (TopFilmDTO) -> Unit
): PagingDataAdapter<TopFilmDTO, TopFilmViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmViewHolder {
        return TopFilmViewHolder( ItemRecyclerFilmListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TopFilmViewHolder, position: Int) {
        var genreTxt = ""
        val film = getItem(position)
        //Set film name
        holder.binding.nameFilm.text = film?.nameRu ?: ""
        //Set film genres.
        film?.genres?.forEach {
            genreTxt = if (genreTxt == "") { it.genre.toString()}
            else { genreTxt + ", " + it.genre.toString()}
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
        }else{
            film?.let {
                Glide.with(holder.binding.poster).load(it.posterUrlPreview).into(holder.binding.poster)
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

class TopFilmViewHolder(val binding: ItemRecyclerFilmListBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<TopFilmDTO>() {
    override fun areItemsTheSame(oldItem: TopFilmDTO, newItem: TopFilmDTO): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: TopFilmDTO, newItem: TopFilmDTO): Boolean = oldItem == newItem
}