package com.example.movie_catalog.ui.viewer_seasons.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.repositary.api.home.seasons.EpisodeDTO
import com.example.movie_catalog.databinding.ItemViewerSeasonRecyclerBinding
import javax.inject.Inject

class EpisodeAdapter @Inject constructor() : RecyclerView.Adapter<ImageViewHolder>() {

    private var episodes: List<EpisodeDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listEpisodes: List<EpisodeDTO>) {
        episodes = listEpisodes
        notifyDataSetChanged()
//        Log.d("KDS", "ImagesAdapter, set new list. Size list=${images.size} ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemViewerSeasonRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = episodes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")

// Show name
        if (episodes[position].nameRu != null && episodes[position].nameEn != null){
            holder.binding.tvNameEn.text = episodes[position].episodeNumber.toString() + " " +
                    App.context.getString(R.string.episode) + ", " + episodes[position].nameEn.toString()
            holder.binding.tvNameRu.text = episodes[position].nameRu.toString()
        } else if (episodes[position].nameRu == null && episodes[position].nameEn != null){
            holder.binding.tvNameEn.text = episodes[position].episodeNumber.toString() + " " +
                    App.context.getString(R.string.episode) + ", " + episodes[position].nameEn.toString()
            holder.binding.tvNameRu.visibility = View.INVISIBLE
            holder.binding.tvNameRu.layoutParams.height = 0
        } else {
            holder.binding.tvNameEn.text = episodes[position].episodeNumber.toString() + " " +
                    App.context.getString(R.string.episode) + ", " + episodes[position].nameRu.toString()
            holder.binding.tvNameRu.visibility = View.INVISIBLE
            holder.binding.tvNameRu.layoutParams.height = 0
        }
        if (episodes[position].synopsis == null) {
            holder.binding.tvDescription.visibility = View.INVISIBLE
            holder.binding.tvDescription.layoutParams.height = 0
        } else {
            holder.binding.tvDescription.text = episodes[position].synopsis
        }
        if (episodes[position].releaseDate == null) {
            holder.binding.tvData.visibility = View.INVISIBLE
            holder.binding.tvData.layoutParams.height = 0
        } else {
            holder.binding.tvData.text = episodes[position].releaseDate
        }
    }
}

class ImageViewHolder(val binding: ItemViewerSeasonRecyclerBinding) : RecyclerView.ViewHolder(binding.root)