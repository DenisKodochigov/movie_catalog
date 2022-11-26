package com.example.movie_catalog.ui.gallery.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.databinding.ItemGalleryTabBinding
import com.example.movie_catalog.entity.Tab
import javax.inject.Inject

class TabsAdapter @Inject constructor(): RecyclerView.Adapter<TabsViewHolder>() {

    private var tabs: List<Tab> = emptyList()

    fun setList( listTab: List<Tab>){
        tabs = listTab
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabsViewHolder {
        return TabsViewHolder(ItemGalleryTabBinding.inflate(
            LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: TabsViewHolder, position: Int) {
        holder.binding.tvGalleryTabQuantity.text = tabs[position].quantity.toString()
        holder.binding.tvGalleryTabName.text = tabs[position].nameTab
    }

    override fun getItemCount()= tabs.size
}

class TabsViewHolder(val binding: ItemGalleryTabBinding): RecyclerView.ViewHolder(binding.root)