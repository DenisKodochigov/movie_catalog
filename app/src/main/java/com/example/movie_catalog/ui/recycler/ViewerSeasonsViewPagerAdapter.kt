package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.home.seasons.SeasonDTO
import com.example.movie_catalog.databinding.ItemViewerSeasonBinding

class ViewerSerialViewPagerAdapter(private val listSeason: List<SeasonDTO>): RecyclerView.Adapter<SeasonPagerVH>() {

    override fun getItemCount() = listSeason.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonPagerVH {
        return SeasonPagerVH( ItemViewerSeasonBinding.inflate( LayoutInflater.from(parent.context),
            parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeasonPagerVH, position: Int) {

        val episodeAdapter = EpisodeAdapter()
        holder.binding.recycler.layoutManager = LinearLayoutManager(App.context, RecyclerView.VERTICAL, false)
        holder.binding.recycler.adapter = episodeAdapter
        episodeAdapter.setList(listSeason[position].episodes!!)
        holder.binding.tvHeader.text = listSeason[position].number.toString() + " " +
                App.context.getString(R.string.viewer_seasons_season) + ", " +
                listSeason[position].episodes?.size.toString() + " " +
                App.context.getString(R.string.quantity_series)
    }
}

class SeasonPagerVH(val binding: ItemViewerSeasonBinding): RecyclerView.ViewHolder(binding.root)