package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.Constants.QTY_CARD
import com.example.movie_catalog.databinding.FilmBinding
import com.example.movie_catalog.entity.Film
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
            FilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        //Load small poster.
        film?.let {
            Glide.with(holder.binding.poster).load(it.posterUrlPreview).into(holder.binding.poster)
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

class FilmViewHolder(val binding: FilmBinding) : RecyclerView.ViewHolder(binding.root)
