package com.example.movie_catalog.ui.filmography.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.databinding.ItemFilmographyViewerBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person

class FilmographyViewPagerAdapter (val person: Person, private val onClick: (Film) -> Unit ):
    RecyclerView.Adapter<PagerHV>() {

    override fun getItemCount() = person.tabs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHV {
        return PagerHV( ItemFilmographyViewerBinding.inflate( LayoutInflater.from(parent.context),
            parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerHV, position: Int) {

        val filmAdapter = FilmographyRecyclerAdapter { film -> onClickImagesAdapter(film)}
//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
        holder.binding.recyclerImage.layoutManager = LinearLayoutManager(App.context,
                                                    RecyclerView.VERTICAL, false)
        holder.binding.recyclerImage.adapter = filmAdapter

        Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
        filmAdapter.setList(person.films.filter {
                film -> film.professionKey == person.tabs[position].key })
    }

    private fun onClickImagesAdapter(film: Film) {
        onClick(film)
    }
}

class PagerHV(val binding: ItemFilmographyViewerBinding): RecyclerView.ViewHolder(binding.root)