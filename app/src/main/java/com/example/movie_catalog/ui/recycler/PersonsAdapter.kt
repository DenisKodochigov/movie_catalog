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
//            R.layout.item_film_page_person -> PersonGridVH(inflater.inflate(viewType, parent, false))
//            R.layout.item_person_list -> PersonListVH(inflater.inflate(viewType, parent, false))
            R.layout.item_film_page_person -> PersonGridVH(
                ItemFilmPagePersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_person_list -> PersonListVH(
                ItemPersonListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = linkers.getOrNull(position)?.person

        when (holder) {
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

    override fun getItemViewType(position: Int): Int {
        return when (sizeGird) {
            0 -> R.layout.item_person_list
            in 1..100 -> R.layout.item_film_page_person
            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }
}

class PersonListVH(val binding: ItemPersonListBinding) : RecyclerView.ViewHolder(binding.root)
class PersonGridVH(val binding: ItemFilmPagePersonBinding) : RecyclerView.ViewHolder(binding.root)
