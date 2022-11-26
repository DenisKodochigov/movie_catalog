package com.example.movie_catalog.ui.full_list_films.recyclerViewFLF

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.databinding.IncludeFilmInfoStateLoadBinding

class StateAdapterTopFilm : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = IncludeFilmInfoStateLoadBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }
}

class LoadStateViewHolder(binding: IncludeFilmInfoStateLoadBinding) :
    RecyclerView.ViewHolder(binding.root)