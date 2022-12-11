package com.example.movie_catalog.ui.film_info.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemFilmInfoPersonBinding
import com.example.movie_catalog.data.api.film_info.PersonDTO
import javax.inject.Inject

class PersonAdapter @Inject constructor(private val onClick: (PersonDTO) -> Unit,
               val sizeGird:Int, val whatRole: Int): RecyclerView.Adapter<PersonViewHolder>()
{
    private var people: List<PersonDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(people: List<PersonDTO>) {
        this.people = people
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemFilmInfoPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val person = people.getOrNull(position)
        //Set actor name
        holder.binding.tvActor.text = person?.nameRu ?: ""
        //Set actor role.
        if (whatRole == 1) holder.binding.tvRole.text = person?.description ?: ""
        else if (whatRole == 2) holder.binding.tvRole.text = person?.professionText ?: ""

        //Load small poster. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(holder.binding.photo,person?.posterUrl, R.dimen.card_people_radius)

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            person?.let {
                onClick(person)
            }
        }
    }

    override fun getItemCount(): Int{
        return if (people.size > sizeGird -1)  sizeGird
                else people.size
    }
}

class PersonViewHolder(val binding: ItemFilmInfoPersonBinding) : RecyclerView.ViewHolder(binding.root)