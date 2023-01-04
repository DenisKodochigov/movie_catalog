package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemListFilmBotBinding
import com.example.movie_catalog.databinding.ItemListFilmRightBinding
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import javax.inject.Inject

class ListFilmAdapter @Inject constructor(
    private val quantityItem: Int = 0,
    private val mode: ModeViewer = ModeViewer.FILM,
    private val onClick: (film: Film) -> Unit,
    private val onClickAll: (kit: Kit) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var linkers: List<Linker> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listLinker: List<Linker>) {
        linkers = listLinker
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_list_film_bot -> ListVH(
                ItemListFilmBotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_list_film_right -> FilmographyVH(
                ItemListFilmRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListVH -> {
                if (quantityItem > 0 && position > Constants.HOME_QTY_FILMCARD-2){
                    holder.binding.inclFilm.root.visibility = View.INVISIBLE
                    holder.binding.inclShowAll.root.visibility = View.VISIBLE
                    holder.binding.inclShowAll.oval.setOnClickListener {
                        linkers[position].kit?.let { kit -> onClickAll(kit) }
                    }
                }else {
                    holder.binding.inclFilm.root.visibility = View.VISIBLE
                    holder.binding.inclShowAll.root.visibility = View.INVISIBLE
                    var film = linkers.getOrNull(position)?.film
                    if (mode == ModeViewer.SIMILAR) film = linkers.getOrNull(position)?.similarFilm
                    film?.let {
                        //Set film name
                        holder.binding.inclFilm.nameFilm.text = film.nameRu ?: ""
        //Set film genres.
                        holder.binding.inclFilm.genreFilm.text = film.genresTxt()
        //Set viewed flag
                        if (film.viewed) {
                            holder.binding.inclFilm.ivViewed.visibility = View.VISIBLE
                            holder.binding.inclFilm.poster.foreground =
                                App.context.getDrawable(R.drawable.gradientviewed)
                        }
        //Set rating
                        if (film.rating != null) holder.binding.inclFilm.tvRating.text =
                            film.rating.toString()
                        else holder.binding.inclFilm.tvRating.visibility = View.INVISIBLE
        //Set action on click item recyclerView
                        holder.binding.root.setOnClickListener {
                            onClick(film)
                        }
                    }
                    //Load small poster. Before load image, show waiting animation.
                    val animationCard = LoadImageURLShow()
                    animationCard.setAnimation(
                        holder.binding.inclFilm.poster,
                        film?.posterUrlPreview,
                        R.dimen.card_film_radius
                    )
                }
            }
            is FilmographyVH -> {
                val filmF = linkers[position].film
//        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")
                val animationCard = LoadImageURLShow()
                filmF?. let { film ->
                    with(holder.binding) {
//Set viewed flag
                        if (film.viewed) {
                            ivViewed.background = App.context.getDrawable(R.drawable.icon_viewed)
                            ivViewed.visibility = View.VISIBLE
                        }else{
                            ivViewed.visibility = View.INVISIBLE
                            ivViewed.layoutParams.height = 0
                        }
//Set rating
                        if (film.rating != null) tvRating.text = film.rating.toString()
                        else tvRating.visibility = View.INVISIBLE

                        animationCard.setAnimation( poster, film.posterUrlPreview, R.dimen.gallery_list_small_card_radius)
//Set film name
                        if (film.nameRu != null) nameFilm.text = film.nameRu
                        else if (film.nameEn != null) nameFilm.text = film.nameEn
//Set film date
                        if (film.startYear != null) startYear.text = film.startYear.toString() + " " + film.genresTxt()
//Set action on click item recyclerView
                        poster.setOnClickListener {
                            onClick(film)
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mode) {
            ModeViewer.FILM -> R.layout.item_list_film_bot
            ModeViewer.SIMILAR -> R.layout.item_list_film_bot
            ModeViewer.FILMOGRAPHY -> R.layout.item_list_film_right
            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }
    override fun getItemCount(): Int {
        return if (quantityItem > 0 && linkers.size > quantityItem - 1) {
            quantityItem
        } else {
            linkers.size
        }
    }
}

class ListVH(val binding: ItemListFilmBotBinding): RecyclerView.ViewHolder(binding.root)
class FilmographyVH(val binding: ItemListFilmRightBinding):
    RecyclerView.ViewHolder(binding.root)