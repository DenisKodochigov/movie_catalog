package com.example.movie_catalog.ui.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.databinding.IncludeFilmPageStateLoadBinding

/*
The adapter is used in conjunction with the paging adapter recycleview
 */

class  StateAdapterTopFilm : LoadStateAdapter<LoadStateViewHolder>() {

    lateinit var contextClass: Context

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        contextClass = parent.context
        val binding = IncludeFilmPageStateLoadBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return LoadStateViewHolder(binding)
    }
}

class LoadStateViewHolder(binding: IncludeFilmPageStateLoadBinding) :
    RecyclerView.ViewHolder(binding.root)