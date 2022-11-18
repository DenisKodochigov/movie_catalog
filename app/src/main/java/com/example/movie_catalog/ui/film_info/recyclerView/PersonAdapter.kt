package com.example.movie_catalog.ui.film_info.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.Constants
import com.example.movie_catalog.databinding.ItemRecyclerPersonBinding
import com.example.movie_catalog.entity.filminfo.person.Person
import javax.inject.Inject

class PersonAdapter @Inject constructor(private val onClick: (Person) -> Unit
                    ): RecyclerView.Adapter<PersonViewHolder>()
{
    private var people: List<Person> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(people: List<Person>) {
        this.people = people
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemRecyclerPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val actor = people.getOrNull(position)
        //Set actor name
        holder.binding.tvActor.text = actor?.nameRu ?: ""
        //Set actor role.
        holder.binding.tvRole.text = actor?.professionText ?: ""

        //Load small poster. Before load image, show waiting animation.
        val animationCard = holder.binding.photo.background as AnimationDrawable
        if (actor?.posterUrl == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        }else{
            actor.let {
                Glide.with(holder.binding.photo).load(it.posterUrl).into(holder.binding.photo)
                animationCard.stop()
            }
        }

        //Set action on click item recyclerView
        holder.binding.root.setOnClickListener {
            actor?.let {
                onClick(actor)
            }
        }
    }

    override fun getItemCount(): Int{
        return if (people.size > Constants.QTY_CARD -1) {
            Constants.QTY_CARD
        }else{
            people.size
        }
    }
}

class PersonViewHolder(val binding: ItemRecyclerPersonBinding) : RecyclerView.ViewHolder(binding.root)