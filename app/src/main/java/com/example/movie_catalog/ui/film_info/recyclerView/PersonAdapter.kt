package com.example.movie_catalog.ui.film_info.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.ItemRecyclerPersonBinding
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import javax.inject.Inject

class PersonAdapter @Inject constructor(private val onClick: (PersonDTO) -> Unit,
                                        val sizeGird:Int, val whoteRole: Int): RecyclerView.Adapter<PersonViewHolder>()
{
    private var people: List<PersonDTO> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListFilm(people: List<PersonDTO>) {
        this.people = people
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemRecyclerPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val person = people.getOrNull(position)
        //Set actor name
        holder.binding.tvActor.text = person?.nameRu ?: ""
        //Set actor role.
        if (whoteRole == 1) holder.binding.tvRole.text = person?.description ?: ""
        else if (whoteRole == 2) holder.binding.tvRole.text = person?.professionText ?: ""

        //Load small poster. Before load image, show waiting animation.
        val animationCard = holder.binding.photo.background as AnimationDrawable
        if (person?.posterUrl == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        }else{
            person.let {
                Glide.with(holder.binding.photo).load(it.posterUrl).into(holder.binding.photo)
                animationCard.stop()
            }
        }

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

class PersonViewHolder(val binding: ItemRecyclerPersonBinding) : RecyclerView.ViewHolder(binding.root)