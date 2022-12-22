package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.databinding.ItemGalleryViewerBinding
import com.example.movie_catalog.entity.Gallery
import com.example.movie_catalog.entity.filminfo.ImageFilm

class GalleryViewPagerAdapter(val gallery: Gallery, private val onClick: (String) -> Unit ):
    RecyclerView.Adapter<GalleryPagerVH>() {

    override fun getItemCount() = gallery.tabs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPagerVH {
        return GalleryPagerVH(ItemGalleryViewerBinding.inflate( LayoutInflater.from(parent.context),
            parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GalleryPagerVH, position: Int) {
        val imageAdapter = ImagesAdapter ({ onClickImagesAdapter()},1)
//        val imageAdapter = GalleryImagesAdapter { image -> onClickImagesAdapter(image)}
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
//        Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
        val listImage = gallery.images.filter { it.imageGroup == gallery.tabs[position].first }
        if (listImage.isEmpty()){
            imageAdapter.setList(listOf(ImageFilm("","", null),
                                        ImageFilm("","", null)))
        }else{
            imageAdapter.setList(listImage)
        }
    }
    private fun onClickImagesAdapter() {
        onClick("")
    }
//    private fun onClickImagesAdapter(imageURL: String) {
//        onClick(imageURL)
//    }
}

class GalleryPagerVH(val binding: ItemGalleryViewerBinding): RecyclerView.ViewHolder(binding.root)
