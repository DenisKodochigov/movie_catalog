package com.example.movie_catalog.ui.gallery.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.ItemGalleryTabBinding
import com.example.movie_catalog.entity.filminfo.Tab
import javax.inject.Inject

class TabsAdapter @Inject constructor(private val onClick:(Tab) -> Unit
                ): RecyclerView.Adapter<TabsViewHolder>() {

    private var tabs: List<Tab> = emptyList()

    fun setList( listTab: List<Tab>){
        tabs = listTab
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabsViewHolder {
        return TabsViewHolder(ItemGalleryTabBinding.inflate(
            LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: TabsViewHolder, position: Int) {
        holder.binding.tvGalleryTabQuantity.text = tabs[position].imagesUrl?.total.toString()
        holder.binding.tvGalleryTabName.text = tabs[position].nameTabDisplay
        holder.binding.root.background = (R.drawable.gallery_tab_selected_rectangle).toDrawable()//"@drawable/gallery_tab_selected_rectangle"
//Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick(tabs[position])
        }
    }

    override fun getItemCount()= tabs.size
}

class TabsViewHolder(val binding: ItemGalleryTabBinding): RecyclerView.ViewHolder(binding.root)