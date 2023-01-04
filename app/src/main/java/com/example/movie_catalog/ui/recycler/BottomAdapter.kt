package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.data.room.CollectionFilmDB
import com.example.movie_catalog.databinding.ItemBottomRecyclerBinding
import javax.inject.Inject


class BottomAdapter @Inject constructor(private val onClick: (collection: CollectionFilmDB) -> Unit,
): RecyclerView.Adapter<BottomVH>() {

    private var collections: List<CollectionFilmDB> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listCollection: List<CollectionFilmDB>) {
        collections = listCollection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomVH {
        return BottomVH( ItemBottomRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: BottomVH, position: Int) {
        val collection = collections.getOrNull(position)
        collection?.let {
            holder.binding.checkBox.text = collection.name ?: ""
            holder.binding.tvCount.text = collection.count.toString()
            holder.binding.checkBox.isChecked = collection.included
            holder.binding.checkBox.setOnClickListener {
                onClick(collection)
            }
        }
    }
    override fun getItemCount() = collections.size
}

class BottomVH(val binding: ItemBottomRecyclerBinding): RecyclerView.ViewHolder(binding.root)