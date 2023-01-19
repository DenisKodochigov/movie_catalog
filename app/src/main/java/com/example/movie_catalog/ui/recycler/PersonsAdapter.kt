package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemFilmPagePersonBinding
import com.example.movie_catalog.databinding.ItemPersonListBinding
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Person
import javax.inject.Inject
/*
The adapter is used in:
 1. The Film Page Fragment to display a list of person.
 2. The List Person Fragment to display a list of person.

If the size of the list is not specified, then we use the markup for the vertical full list of persons,
if the size is set, then we use the markup to display a grid of persons
 */
class PersonsAdapter @Inject constructor(
    private val onClick: (Person) -> Unit, val sizeGird: Int = 0, val whatRole: Int = 0
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var linkers: List<Linker> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListPerson(listLinker: List<Linker>) {
        linkers = listLinker
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return when (viewType) {
            //The markup to display a grid of persons
            R.layout.item_film_page_person -> PersonGridVH(
                ItemFilmPagePersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            //The markup for the vertical full list of persons
            R.layout.item_person_list -> PersonListVH(
                ItemPersonListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = linkers.getOrNull(position)?.person

        when (holder) {
            //The markup for the vertical full list of persons
            is PersonListVH -> {
                person?.let {
                    //Set Person name
                    holder.binding.personName.text = person.nameRu ?: ""
                    //Set Person job.
                    holder.binding.personJob.text = person.professionText
                    //Set action on click item recyclerView
                    holder.binding.root.setOnClickListener {
                        onClick(person)
                    }
                }
                //Load small poster. Before load image, show waiting animation
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.poster, person?.posterUrl, R.dimen.card_film_radius)
            }
            //The markup to display a grid of persons
            is PersonGridVH -> {
                person?.let {
                    //Set actor name
                    holder.binding.tvActor.text = person.nameRu ?: ""
                    //Set actor role.
                    if (whatRole == 1) holder.binding.tvRole.text = person.description ?: ""
                    else if (whatRole == 2) holder.binding.tvRole.text = person.professionText ?: ""
                    //Set action on click item recyclerView
                    holder.binding.root.setOnClickListener {
                        onClick(person)
                    }
                }
                //Load small poster. Before load image, show waiting animation.
                val animationCard = LoadImageURLShow()
                animationCard.setAnimation(holder.binding.photo, person?.posterUrl, R.dimen.card_people_radius)
            }
            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return if (linkers.size > sizeGird - 1 && sizeGird != 0) sizeGird
        else linkers.size
    }
//    If the size of the list is not specified, then we use the markup for the vertical full list of persons,
//    if the size is set, then we use the markup to display a grid of persons
    override fun getItemViewType(position: Int): Int {
        return when (sizeGird) {
            0 -> R.layout.item_person_list
            in 1..100 -> R.layout.item_film_page_person
            // in case populated with a model we don't know how to display.
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}

class PersonListVH(val binding: ItemPersonListBinding) : RecyclerView.ViewHolder(binding.root)
class PersonGridVH(val binding: ItemFilmPagePersonBinding) : RecyclerView.ViewHolder(binding.root)
