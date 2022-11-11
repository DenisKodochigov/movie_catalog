package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.FilmBinding
import com.example.movie_catalog.entity.Film

class FilmListAdapter: RecyclerView.Adapter<FilmViewHolder>() {

    private var films : List<Film> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(films: List<Film>){
        this.films = films
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            FilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = films.getOrNull(position)
        holder.binding.nameFilm.text = item?.name ?: ""
        holder.binding.genreFilm.text = item?.genre ?: ""
        item?.let {
            Glide.with(holder.binding.poster).load(it.poster).into(holder.binding.poster)
        }
    }
     override fun getItemCount(): Int = films.size
}

class FilmViewHolder(val binding: FilmBinding): RecyclerView.ViewHolder(binding.root)
