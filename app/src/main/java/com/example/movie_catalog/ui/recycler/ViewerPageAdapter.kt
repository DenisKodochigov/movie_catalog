package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
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
/*
The adapter is used in:
 1. The Filmography Fragment to display a list of collections.
 2. The Image Fragment to display a list of images.
 3. The Season Fragment to display a list years, country, genres
 4. The Gallery Fragment to display a list images
 5. The Start Fragment to display a list episodes

To properly connect the necessary modules, the ModeViewer enumeration is used

Each case uses its own screen layout
1. The list of images uses item_viewer_image_start
2. The list of season uses item_viewer_season
3. The list of images on start uses item_viewer_image_start
4. The list of gallery uses item_viewer

 */
class ViewerPageAdapter (private val mode: ModeViewer, private val onClick: (Any?) -> Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: Any? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listItems: Any) {
        items = listItems
        notifyDataSetChanged()
    }
    //Determining the size of the list
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
    //Assign the holder depending on the adapter's operating mode
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // The list of start images
            R.layout.item_viewer_image_start -> ImageStartHV(ItemViewerImageStartBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of images
            R.layout.item_viewer_image_recycler -> ImageHV(ItemViewerImageRecyclerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of seasons
            R.layout.item_viewer_season -> SeasonVH(ItemViewerSeasonBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of type images for movie
            R.layout.item_viewer -> GalleryVH(ItemViewerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }
    //We connect the markup depending on the mode viewer
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
        //Selecting the current holder
        when (holder) {
            is GalleryVH -> {
                when (mode){
                    //Preparing view tabs for the gallery
                    ModeViewer.GALLERY -> {
                        val gallery = items as Gallery
                        //Creating an adapter for the viewpager body
                        val imageAdapter = SimpleAdapterAny ({ onClickImagesAdapter(null)},1)
                        //Connecting layout manager
                        holder.binding.recyclerImage.layoutManager = GridLayoutManager(App.context, 2).also {
                            //Changing the markup depending on the image number
                            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                override fun getSpanSize(position: Int): Int {
                                    return if (position % 3 == 2) 2
                                    else 1
                                }
                            }
                        }
                        //Connecting adapter to recyclerview
                        holder.binding.recyclerImage.adapter = imageAdapter
                        //Creating a list corresponding to this tab
                        val listImage = gallery.images.filter { it.imageGroup == gallery.tabs[position].first }
                        ////Creating a list corresponding to this tab. Loading the created list.
                        // If the list is empty, load the stubs
                        if (listImage.isEmpty()){
                            imageAdapter.setList(listOf(
                                ImageFilm("","", null),
                                ImageFilm("","", null)
                            ))
                        }else{
                            imageAdapter.setList(listImage)
                        }
                    }
                    //Preparing view tabs for the filmography
                    ModeViewer.FILMOGRAPHY -> {
                        val person = items as FilmographyData
                        //Creating an adapter for the viewpager body
                        val filmAdapter = ListFilmAdapter (0, ModeViewer.FILMOGRAPHY,
                            { film -> onClickImagesAdapter(film)},{})
                        //Connecting layout manager
                        holder.binding.recyclerImage.layoutManager = LinearLayoutManager(App.context,
                            RecyclerView.VERTICAL, false)
                        //Connecting adapter
                        holder.binding.recyclerImage.adapter = filmAdapter
                        //Creating a list corresponding to this tab. Loading the created list.
                        filmAdapter.setListFilm(person.linkers.filter{ it.profKey == person.tabsKey[position].first})
                    }
                    else -> {}
                }
            }
            is ImageHV -> {
                //Image output in Image fragment
                val gallery = items as Gallery
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.photo, gallery.images[position].imageUrl, R.dimen.card_gallery_icon_radius, false)
            }
            is ImageStartHV -> {
                //Image output in start fragment
                val listImageStart = items as List<ImageStart>
                holder.binding.ivIcon.setImageResource(listImageStart[position].imageResource!!)
                holder.binding.ivIndicator.setImageResource(listImageStart[position].imageIndicator!!)
                holder.binding.tvSignature.text =
                    App.context.resources.getString(listImageStart[position].signature!!)
            }
            is SeasonVH -> {
                //Output of information on the series in the series
                val film = items as Film
                //Creating an adapter for the viewpager body
                val episodeAdapter = SimpleAdapterAny({})
                //Connecting layout manager
                holder.binding.recycler.layoutManager = LinearLayoutManager(App.context,
                    RecyclerView.VERTICAL, false)
                //Connecting adapter
                holder.binding.recycler.adapter = episodeAdapter
                //Loading the created list.
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