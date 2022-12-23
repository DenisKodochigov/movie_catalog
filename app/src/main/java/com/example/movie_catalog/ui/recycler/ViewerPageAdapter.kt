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
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.entity.filminfo.ImageFilm


class ViewerPageAdapter (val mode: ModeViewer, private val onClick: (Any?) -> Unit ):
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
                if (size == 0) size = (items as Gallery).images.size
            }
            is FilmographyData -> size = (items as FilmographyData).tabsKey.size
            is Film -> size = (items as Film).listSeasons!!.size
        }
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_filmography_viewer -> FilmogrVH(
                ItemFilmographyViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_viewer_image -> ImageHV(
                ItemViewerImageRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_viewer_season -> SeasonVH(
                ItemViewerSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_gallery_viewer -> GalleryVH(
                ItemGalleryViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mode) {
            ModeViewer.FILMOGRAPHY -> R.layout.item_filmography_viewer
            ModeViewer.GALLERY -> R.layout.item_gallery_viewer
            ModeViewer.IMAGE -> R.layout.item_viewer_image
            ModeViewer.SEASON -> R.layout.item_viewer_season
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is FilmogrVH -> {
                val person = items as FilmographyData
                val filmAdapter = FilmographyRecyclerAdapter { film -> onClickImagesAdapter(film)}
//        Log.d("KDS", "ViewPagerAdapter, onBindViewHolder start. Position=$position")
                holder.binding.recyclerImage.layoutManager = LinearLayoutManager(App.context,
                    RecyclerView.VERTICAL, false)
                holder.binding.recyclerImage.adapter = filmAdapter

                Log.d("KDS", "ViewPagerAdapter, load image tab[position]=$position ")
                filmAdapter.setList(person.linkers.filter{ it.profKey == person.tabsKey[position].first})
            }

            is ImageHV -> {
                val gallery = items as Gallery
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.photo, gallery.images[position].imageUrl, R.dimen.card_gallery_icon_radius, false)
            }
            is SeasonVH -> {
                val film = items as Film
                val episodeAdapter = EpisodeAdapter()
                holder.binding.recycler.layoutManager = LinearLayoutManager(App.context, RecyclerView.VERTICAL, false)
                holder.binding.recycler.adapter = episodeAdapter

                film.listSeasons?.get(position)?.let {
                    episodeAdapter.setList(it.episodes!!)
                    holder.binding.tvHeader.text = it.number.toString() + " " + App.context.getString(R.string.viewer_seasons_season) + ", " +
                            it.episodes?.size.toString() + " " + App.context.getString(R.string.quantity_series)
                }
            }

            is GalleryVH -> {
                val gallery = items as Gallery
                val imageAdapter = ImagesAdapter ({ onClickImagesAdapter(null)},1)
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
            else -> {}
        }
    }

    private fun onClickImagesAdapter(film: Film?) {
        onClick(film)
    }
}
class FilmogrVH(val binding: ItemFilmographyViewerBinding): RecyclerView.ViewHolder(binding.root)
class ImageHV(val binding: ItemViewerImageRecyclerBinding): RecyclerView.ViewHolder(binding.root)
class SeasonVH(val binding: ItemViewerSeasonBinding): RecyclerView.ViewHolder(binding.root)
class GalleryVH(val binding: ItemGalleryViewerBinding): RecyclerView.ViewHolder(binding.root)