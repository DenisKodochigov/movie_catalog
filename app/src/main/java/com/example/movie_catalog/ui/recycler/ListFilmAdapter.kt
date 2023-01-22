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

/*
The adapter is used in:
 1. The Profile Fragment to display a list of films for collection.
 2. The Film Page Fragment to display a list similar movie for the movie.
 3. The Home Fragment to display a list movies for selections
 4. The Kit Fragment to display a list premieres
 5. The List Film Fragment to display a list movies
 6. The Person Fragment to display a list persons
 7. The Filmography Fragment to display a list movie

To properly connect the necessary modules, the ModeViewer enumeration is used

 */

class ListFilmAdapter @Inject constructor(
    private val quantityItem: Int = 0,              //The number of displayed items in the list
    private val mode: ModeViewer = ModeViewer.FILM, //For what context is the adapter called
    private val onClick: (film: Film) -> Unit,      //Call back on click item recyclerview
    private val onClickLast: (kit: Kit) -> Unit     //Call back on click last item recyclerview
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //Working list items
    private var linkers: List<Linker> = emptyList()
    //Determining the size of the list
    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(listLinker: List<Linker>) {
        linkers = listLinker
        notifyDataSetChanged()
    }
    //Assign the holder depending on the adapter's operating mode
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
                holder.binding.inclFilm.root.visibility = View.INVISIBLE
                holder.binding.inclShowAll.root.visibility = View.INVISIBLE
                holder.binding.inclClear.root.visibility = View.INVISIBLE
                //Add card "Show all"
                if (quantityItem > 0 && mode != ModeViewer.PROFILE && position > Constants.HOME_QTY_FILM_CARD-2){
                    holder.binding.inclShowAll.root.visibility = View.VISIBLE
                    holder.binding.inclShowAll.oval.setOnClickListener {
                        linkers[position].kit?.let { kit -> onClickLast(kit) }
                    }
                //Add card "Clear kit"
                } else if (quantityItem > 0 && mode == ModeViewer.PROFILE &&
                    (( position > Constants.PROFILE_QTY_FILM_CARD-2) || position == linkers.size - 1)){
                    holder.binding.inclClear.root.visibility = View.VISIBLE
                    holder.binding.inclClear.oval.setOnClickListener {
                        linkers[position].kit?.let { kit -> onClickLast(kit) }
                    }
                //Add card "Clear kit"
                } else if (quantityItem == 0 && mode == ModeViewer.PROFILE && position == linkers.size - 1){
                    holder.binding.inclClear.root.visibility = View.VISIBLE
                    holder.binding.inclClear.oval.setOnClickListener {
                        linkers[position].kit?.let { kit -> onClickLast(kit) }
                    }
                //Defining how to display the list
                }else {
                    holder.binding.inclFilm.root.visibility = View.VISIBLE
                    var film = linkers.getOrNull(position)?.film
                    if (mode == ModeViewer.SIMILAR) film = linkers.getOrNull(position)?.similarFilm
                    film?.let {
                        //Set film name
                        holder.binding.inclFilm.nameFilm.text =
                            film.nameRu ?: film.nameEn ?: film.nameOriginal
                        //Set film genres.
                        holder.binding.inclFilm.genreFilm.text = film.genresTxt()
                         //Set viewed flag
                        if (film.viewed) {
                            holder.binding.inclFilm.ivViewed.visibility = View.VISIBLE
                            holder.binding.inclFilm.poster.foreground =
                                App.context.getDrawable(R.drawable.gradientviewed)
                        } else {
                            holder.binding.inclFilm.poster.foreground = null
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
                    animationCard.setAnimation(holder.binding.inclFilm.poster,
                        film?.posterUrlPreview, R.dimen.card_film_radius)
                }
            }
            is FilmographyVH -> {
                val filmF = linkers[position].film
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
                        nameFilm.text = film.nameRu ?: film.nameEn ?: film.nameOriginal
                        //Set film date
                        if (film.year != null) startYear.text = film.year.toString() + " " + film.genresTxt()
                        else startYear.text = film.genresTxt()
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
            ModeViewer.PROFILE -> R.layout.item_list_film_bot
            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }

    override fun getItemCount(): Int {
        //If it is necessary to display a limited list, then we carry out the appropriate checks
        return if (quantityItem > 0 && linkers.size > quantityItem - 1) {
            quantityItem
        } else {
            linkers.size
        }
    }
}

class ListVH(val binding: ItemListFilmBotBinding): RecyclerView.ViewHolder(binding.root)
class FilmographyVH(val binding: ItemListFilmRightBinding): RecyclerView.ViewHolder(binding.root)