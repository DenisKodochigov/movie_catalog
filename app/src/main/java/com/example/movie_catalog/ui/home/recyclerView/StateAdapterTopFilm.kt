package com.example.movie_catalog.ui.home.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.databinding.IncludeStateLoadBinding

class StateAdapterTopFilm: LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = IncludeStateLoadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }
}

class LoadStateViewHolder(binding: IncludeStateLoadBinding) : RecyclerView.ViewHolder(binding.root)