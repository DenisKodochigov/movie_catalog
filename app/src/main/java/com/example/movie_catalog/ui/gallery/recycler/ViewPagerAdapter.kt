package com.example.movie_catalog.ui.gallery.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.databinding.FragmentGalleryPageBinding
import com.example.movie_catalog.entity.filminfo.Gallery

class ViewPagerAdapter(val gallery: Gallery): RecyclerView.Adapter<PagerHV>() {

    override fun getItemCount() = gallery.tabs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHV {
        return PagerHV(FragmentGalleryPageBinding.inflate( LayoutInflater.from(parent.context),
            parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PagerHV, position: Int) {

        val imageAdapter = ImagesAdapter()
//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
        holder.binding.recyclerImage.layoutManager = GridLayoutManager(App.context, 2).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 3 == 2) 2
                    else 1
                }
            }
        }

        holder.binding.recyclerImage.adapter = imageAdapter
        Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
        imageAdapter.setList(gallery.tabs[position].imagesUrl!!.items)
    }
}

class PagerHV(val binding: FragmentGalleryPageBinding ): RecyclerView.ViewHolder(binding.root)
