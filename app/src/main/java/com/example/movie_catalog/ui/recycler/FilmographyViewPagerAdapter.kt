package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.databinding.ItemFilmographyViewerBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmographyData

class FilmographyViewPagerAdapter (val person: FilmographyData, private val onClick: (Film) -> Unit ):
    RecyclerView.Adapter<FilmographyVH>() {

    override fun getItemCount() = person.tabsKey.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyVH {
        return FilmographyVH( ItemFilmographyViewerBinding.inflate( LayoutInflater.from(parent.context),
            parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FilmographyVH, position: Int) {

        val filmAdapter = FilmographyRecyclerAdapter { film -> onClickImagesAdapter(film)}
//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
        holder.binding.recyclerImage.layoutManager = LinearLayoutManager(App.context,
                                                    RecyclerView.VERTICAL, false)
        holder.binding.recyclerImage.adapter = filmAdapter

        Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
        filmAdapter.setList(person.linkers.filter{ it.profKey == person.tabsKey[position].first})
    }

    private fun onClickImagesAdapter(film: Film) {
        onClick(film)
    }
}

class FilmographyVH(val binding: ItemFilmographyViewerBinding): RecyclerView.ViewHolder(binding.root)