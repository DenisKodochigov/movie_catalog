package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.Constants.QTY_CARD
import com.example.movie_catalog.databinding.ItemRecyclerFilmListBinding
import com.example.movie_catalog.entity.home.premieres.Film
import javax.inject.Inject

class FilmListAdapter @Inject constructor( private val onClick: (Film) -> Unit
                                        ): RecyclerView.Adapter<FilmViewHolder>()
{
    private var films: List<Film> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(films: List<Film>) {
        this.films = films
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            ItemRecyclerFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        var genreTxt = ""
        val film = films.getOrNull(position)

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

    override fun getItemCount(): Int{
        return if (films.size > QTY_CARD-1) {
            QTY_CARD
        }else{
            films.size
        }
    }
}

class FilmViewHolder(val binding: ItemRecyclerFilmListBinding) : RecyclerView.ViewHolder(binding.root)
