package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.data.api.home.seasons.EpisodeDTO
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.databinding.*
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.RecyclerData
import com.example.movie_catalog.entity.filminfo.ImageFilm
import javax.inject.Inject

/*
The adapter is used in:
 1. The Film Page Fragment to display a list of collections.
 2. The Film Page Fragment to display a list of images.
 3. The Setting Fragment to display a list years, country, genres
 4. The Gallery Fragment to display a list images
 5. The List Film Fragment to display a list episodes


Each case uses its own screen layout
1. The list of collections with checkbox uses item_bottom_recycler
2. The list of years uses item_bottom_recycler_years
3. The list of collections uses item_profile_card_collections
4. The list of country and genres uses item_bottom_recycler_text
5. The list of episodes uses item_bottom_recycler_text
6. The list of images uses item_image_recycler
 */


class SimpleAdapterAny @Inject constructor(private val onClick: (Any) -> Unit, private val mode: Int = 0
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //List of displayed objects in the recycler view
    private var items: List<Any> = emptyList()
    private lateinit var contextClass: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }
    //Assign holder depending on the type of input data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        contextClass = parent.context
        return when (viewType) {
            // The list of collections
            R.layout.item_profile_card_collections -> CollectionVH( ItemProfileCardCollectionsBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            // The list of collections with checkbox
            R.layout.item_bottom_recycler -> BottomAnyVH( ItemBottomRecyclerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of years
            R.layout.item_bottom_recycler_years -> BottomYearsVH(ItemBottomRecyclerYearsBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of country and genres
            R.layout.item_bottom_recycler_text -> BottomTextVH(ItemBottomRecyclerTextBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of episodes
            R.layout.item_viewer_season_recycler -> EpisodeVH(ItemViewerSeasonRecyclerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            //The list of episodes
            R.layout.item_image_recycler -> ImagesVH(ItemImageRecyclerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }
    //We connect the markup depending on the type of input data
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CollectionDB -> R.layout.item_bottom_recycler        // The list of collections with checkbox
            is RecyclerData -> R.layout.item_bottom_recycler_years  // The list of years
            is Collection -> R.layout.item_profile_card_collections // The list of collections
            is EpisodeDTO -> R.layout.item_viewer_season_recycler   // The list of episodes
            is ImageFilm -> R.layout.item_image_recycler            // The list of images
            else -> R.layout.item_bottom_recycler_text              // The list of country or genres
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        when (holder){
            // The list of collections with checkbox
            is BottomAnyVH -> {
                if (item is CollectionDB){
                    holder.binding.checkBox.text = item.collection?.name ?: ""
                    holder.binding.tvCount.text = item.collection?.count.toString()
                    holder.binding.checkBox.isChecked = item.collection?.included ?: false
                    holder.binding.checkBox.setOnClickListener { onClick(item) }
                }
            }
            //The list of country or genres
            is BottomTextVH -> {
                when (item) {
                    is CountryIdDTO -> {
                        holder.binding.tvText.text = item.country
                        holder.binding.tvText.setOnClickListener { onClick(item) }
                    }
                    is GenreIdDTO -> {
                        holder.binding.tvText.text = item.genre
                        holder.binding.tvText.setOnClickListener { onClick(item) }
                    }
                }
            }
            //The list of years
            is BottomYearsVH -> {
                when (item) {
                    is RecyclerData -> {
                        holder.binding.tvText.text = item.data.toString()
                        if (item.selected) holder.binding.tvText.typeface = Typeface.DEFAULT_BOLD
                        else holder.binding.tvText.typeface = Typeface.DEFAULT

                        holder.binding.tvText.setOnClickListener {
                            item.data?.let { it1 -> onClick(it1) }
                            item.selected = !item.selected
                            if (item.selected) holder.binding.tvText.typeface = Typeface.DEFAULT_BOLD
                            else holder.binding.tvText.typeface = Typeface.DEFAULT
                        }
                    }
                }
            }
            // The list of collections
            is CollectionVH -> {
                if (item is Collection){
                    holder.binding.tvNameCollection.text = item.name
                    holder.binding.tvCount.text = item.count.toString()
                    holder.binding.iv1.setImageResource(item.image)
                    holder.binding.ivDelCollection.setOnClickListener { onClick(item) }
                    holder.binding.root.setOnClickListener { onClick(item.name) }
                }
            }
            // The list of episodes
            is EpisodeVH -> {
                // Show name
                if (item is EpisodeDTO) {
                    if (item.nameRu != null && item.nameEn != null) {
                        holder.binding.tvNameEn.text = item.episodeNumber.toString() + " " +
                                contextClass.getString(R.string.episode) + ", " + item.nameEn.toString()
                        holder.binding.tvNameRu.text = item.nameRu.toString()
                    } else if (item.nameRu == null && item.nameEn != null) {
                        holder.binding.tvNameEn.text = item.episodeNumber.toString() + " " +
                                contextClass.getString(R.string.episode) + ", " + item.nameEn.toString()
                        holder.binding.tvNameRu.visibility = View.INVISIBLE
                        holder.binding.tvNameRu.layoutParams.height = 0
                    } else {
                        holder.binding.tvNameEn.text = item.episodeNumber.toString() + " " +
                                contextClass.getString(R.string.episode) + ", " + item.nameRu.toString()
                        holder.binding.tvNameRu.visibility = View.INVISIBLE
                        holder.binding.tvNameRu.layoutParams.height = 0
                    }
                    if (item.synopsis == null) {
                        holder.binding.tvDescription.visibility = View.INVISIBLE
                        holder.binding.tvDescription.layoutParams.height = 0
                    } else {
                        holder.binding.tvDescription.text = item.synopsis
                    }
                    if (item.releaseDate == null) {
                        holder.binding.tvData.visibility = View.INVISIBLE
                        holder.binding.tvData.layoutParams.height = 0
                    } else {
                        holder.binding.tvData.text = item.releaseDate
                    }
                }
            }
            // The list of images
            is ImagesVH -> {
                // Show name
                if (item is ImageFilm) {
                    val animationCard = LoadImageURLShow()
                    holder.binding.image.foregroundGravity = Gravity.CENTER_HORIZONTAL
                    with(holder.binding) {
                        if (mode == 0){
                            image.layoutParams.width = root.resources.getDimension(R.dimen.card_gallery_icon_width).toInt()
                            image.layoutParams.height = root.resources.getDimension(R.dimen.card_gallery_icon_height).toInt()
                            image.updateLayoutParams<LinearLayout.LayoutParams> {
                                marginStart = root.resources.getDimension(R.dimen.film_page_gallery_margin_hor).toInt()
                                marginEnd = root.resources.getDimension(R.dimen.film_page_gallery_margin_hor).toInt()
                            }
                            root.layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT
                            animationCard.setAnimation( image,item.previewUrl!!, R.dimen.gallery_list_small_card_radius)
                        } else {
                            root.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
                            if (position % 3 == 2) {
                                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_big_card_width).toInt()
                                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_big_card_height).toInt()
                                root.gravity = Gravity.CENTER_HORIZONTAL
                                animationCard.setAnimation( image, item.imageUrl!!, R.dimen.gallery_list_big_card_radius)
                            } else if (position % 3 == 1) {
                                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                                root.gravity = Gravity.START
                                animationCard.setAnimation(image, item.previewUrl!!, R.dimen.gallery_list_small_card_radius)
                            } else if (position % 3 == 0) {
                                image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                                image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                                root.gravity = Gravity.END
                                animationCard.setAnimation( image, item.previewUrl!!, R.dimen.gallery_list_small_card_radius)
                            }
                            image.updateLayoutParams<LinearLayout.LayoutParams> {
                                marginStart = root.resources.getDimension(R.dimen.gallery_list_small_card_margin_hor).toInt()
                                marginEnd = root.resources.getDimension(R.dimen.gallery_list_small_card_margin_hor).toInt()
                            }
                        }
                        image.setOnClickListener {
                            onClick(0)
                        }
                    }
                }
            }

        }
    }
    //Determining the size of the list
    override fun getItemCount(): Int{
        var sizeList = items.size
        if (items.getOrNull(0) is ImageFilm){
           if (items.size > Constants.HOME_QTY_FILM_CARD - 1 && mode == 0) {
               sizeList = Constants.HOME_QTY_FILM_CARD
            }
        }
        return sizeList
    }
}

class BottomAnyVH(val binding: ItemBottomRecyclerBinding) : RecyclerView.ViewHolder(binding.root)
class BottomTextVH(val binding: ItemBottomRecyclerTextBinding) : RecyclerView.ViewHolder(binding.root)
class BottomYearsVH(val binding: ItemBottomRecyclerYearsBinding) : RecyclerView.ViewHolder(binding.root)
class CollectionVH(val binding: ItemProfileCardCollectionsBinding) : RecyclerView.ViewHolder(binding.root)
class EpisodeVH(val binding: ItemViewerSeasonRecyclerBinding) : RecyclerView.ViewHolder(binding.root)
class ImagesVH(val binding: ItemImageRecyclerBinding) : RecyclerView.ViewHolder(binding.root)