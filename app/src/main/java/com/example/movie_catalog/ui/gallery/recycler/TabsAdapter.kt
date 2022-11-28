package com.example.movie_catalog.ui.gallery.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.adapters.ViewBindingAdapter.setBackground
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.ItemGalleryTabBinding
import com.example.movie_catalog.entity.filminfo.Tab
import javax.inject.Inject

class TabsAdapter @Inject constructor(private val onClick:(Int) -> Unit
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
//Set quantity image
        tabs[position].imagesUrl?.let {
            if (it.total == null) holder.binding.tvGalleryTabQuantity.text = "0"
            else holder.binding.tvGalleryTabQuantity.text = it.total.toString()
        }
//Set name tab
        holder.binding.tvGalleryTabName.text = tabs[position].nameTabDisplay
//        if (position == 1) holder.binding.linearlayout.background = (R.drawable.gallery_tab_selected_rectangle).toDrawable()//"@drawable/gallery_tab_selected_rectangle"
//        else
        holder.binding.linearlayout.setBackgroundColor(R.drawable.gallery_tab_unselected_rectangle)
//        if (position == 1) holder.binding.root.background = (R.drawable.gallery_tab_selected_rectangle).toDrawable()//"@drawable/gallery_tab_selected_rectangle"
//        else holder.binding.root.background = (R.drawable.gallery_tab_unselected_rectangle).toDrawable()
//Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount()= tabs.size
}

class TabsViewHolder(val binding: ItemGalleryTabBinding): RecyclerView.ViewHolder(binding.root)