package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.*
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmographyData
import com.example.movie_catalog.entity.Gallery
import com.example.movie_catalog.entity.ImageStart
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.entity.filminfo.ImageFilm

class ViewerPageAdapter (private val mode: ModeViewer, private val onClick: (Any?) -> Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: Any? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listItems: Any) {
        items = listItems
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        var size = 0
        when (items) {
            is Gallery -> {
                size = (items as Gallery).tabs.size
                if (size == 0 || mode == ModeViewer.IMAGE) size = (items as Gallery).images.size
            }
            is FilmographyData -> size = (items as FilmographyData).tabsKey.size
            is Film -> size = (items as Film).listSeasons!!.size
            is List<*> -> size = (items as List<*>).size
        }
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_viewer_image_start -> ImageStartHV(
                ItemViewerImageStartBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_viewer_image_recycler -> ImageHV(
                ItemViewerImageRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_viewer_season -> SeasonVH(
                ItemViewerSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_viewer -> GalleryVH(
                ItemViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mode) {
            ModeViewer.GALLERY -> R.layout.item_viewer
            ModeViewer.FILMOGRAPHY -> R.layout.item_viewer
            ModeViewer.IMAGE -> R.layout.item_viewer_image_recycler
            ModeViewer.SEASON -> R.layout.item_viewer_season
            ModeViewer.START -> R.layout.item_viewer_image_start
            else -> 0
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is GalleryVH -> {
                when (mode){
                    ModeViewer.GALLERY -> {
                        val gallery = items as Gallery
                        val imageAdapter = ImagesAdapter ({ onClickImagesAdapter(null)},1)
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
                        val listImage = gallery.images.filter { it.imageGroup == gallery.tabs[position].first }
                        if (listImage.isEmpty()){
                            imageAdapter.setList(listOf(
                                ImageFilm("","", null),
                                ImageFilm("","", null)
                            ))
                        }else{
                            imageAdapter.setList(listImage)
                        }
                    }
                    ModeViewer.FILMOGRAPHY -> {
                        val person = items as FilmographyData
                        val filmAdapter = ListFilmAdapter (0, ModeViewer.FILMOGRAPHY,
                            { film -> onClickImagesAdapter(film)},{})
//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
                        holder.binding.recyclerImage.layoutManager = LinearLayoutManager(App.context,
                            RecyclerView.VERTICAL, false)
                        holder.binding.recyclerImage.adapter = filmAdapter

                        Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
                        filmAdapter.setListFilm(person.linkers.filter{ it.profKey == person.tabsKey[position].first})
                    }

                    else -> {}
                }
            }
            is ImageHV -> {
                val gallery = items as Gallery
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.photo, gallery.images[position].imageUrl, R.dimen.card_gallery_icon_radius, false)
            }
            is ImageStartHV -> {
                val listImageStart = items as List<ImageStart>
                holder.binding.ivIcon.setImageResource(listImageStart[position].imageResource!!)
                holder.binding.ivIndicator.setImageResource(listImageStart[position].imageIndicator!!)
                holder.binding.tvSignature.text =
                    App.context.resources.getString(listImageStart[position].signature!!)
            }
            is SeasonVH -> {
                val film = items as Film
                val episodeAdapter = EpisodeAdapter()
                holder.binding.recycler.layoutManager = LinearLayoutManager(App.context, RecyclerView.VERTICAL, false)
                holder.binding.recycler.adapter = episodeAdapter

                film.listSeasons?.get(position)?.let {
                    episodeAdapter.setList(it.episodes!!)
                    holder.binding.tvHeader.text = it.number.toString() + " " + App.context.getString(R.string.viewer_seasons_season) + ", " +
                            it.episodes.size.toString() + " " + App.context.getString(R.string.quantity_series)
                }
            }
            else -> {}
        }
    }

    private fun onClickImagesAdapter(film: Film?) {
        onClick(film)
    }
}
class ImageHV(val binding: ItemViewerImageRecyclerBinding): RecyclerView.ViewHolder(binding.root)
class ImageStartHV(val binding: ItemViewerImageStartBinding): RecyclerView.ViewHolder(binding.root)
class SeasonVH(val binding: ItemViewerSeasonBinding): RecyclerView.ViewHolder(binding.root)
class GalleryVH(val binding: ItemViewerBinding): RecyclerView.ViewHolder(binding.root)