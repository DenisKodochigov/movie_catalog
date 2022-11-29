package com.example.movie_catalog.ui.gallery.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.databinding.FragmentGalleryPageBinding
import com.example.movie_catalog.entity.filminfo.Gallery

class ViewPagerAdapter(val gallery: Gallery): RecyclerView.Adapter<PagerHV>() {

    private val imageAdapter = ImagesAdapter()

    override fun getItemCount() = gallery.tabs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHV {

        val holder =
            PagerHV(FragmentGalleryPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//        holder.binding.recyclerImage.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        holder.binding.recyclerImage.layoutManager = GridLayoutManager(parent.context,2)
        holder.binding.recyclerImage.adapter = imageAdapter

        return holder
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerHV, position: Int) {
//        holder.binding.textV1.text = "item $position"
        gallery.tabs[position].imagesUrl?.let { imageAdapter.setList(it.items) }
//        imageAdapter.setList(gallery.tabs[position].imagesUrl?.items!!)
//        holder.binding.root.setBackgroundResource(colors[position])
    }
}

class PagerHV(val binding: FragmentGalleryPageBinding ): RecyclerView.ViewHolder(binding.root)