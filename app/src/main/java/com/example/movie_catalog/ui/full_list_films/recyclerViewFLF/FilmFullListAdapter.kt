package com.example.movie_catalog.ui.home.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.ItemRecyclerFilmListBinding
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO

class FilmFullListAdapter : RecyclerView.Adapter<FilmFullListViewHolder>() {

    private var filmDTOS: List<FilmDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(filmDTOS: List<FilmDTO>) {
        this.filmDTOS = filmDTOS
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmFullListViewHolder {
        return FilmFullListViewHolder(
            ItemRecyclerFilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilmFullListViewHolder, position: Int) {
        var genreTxt = ""
        val item = filmDTOS.getOrNull(position)
        holder.binding.nameFilm.text = item?.nameRu ?: ""
        item?.genres?.forEach {
            if (genreTxt == "") {
                genreTxt = it.genre.toString()
            } else {
                genreTxt = genreTxt + ", " + it.genre.toString()
            }
        }
        holder.binding.genreFilm.text = genreTxt
        item?.let {
            Glide.with(holder.binding.poster).load(it.posterUrlPreview).into(holder.binding.poster)
        }
    }

    override fun getItemCount()=filmDTOS.size
}

class FilmFullListViewHolder(val binding: ItemRecyclerFilmListBinding) : RecyclerView.ViewHolder(binding.root)
