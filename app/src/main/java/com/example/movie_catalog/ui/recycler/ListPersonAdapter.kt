package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemPersonListBinding
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Person
import javax.inject.Inject

class ListPersonAdapter @Inject constructor(
    private val onClick: (Person) -> Unit
) : RecyclerView.Adapter<ListPersonViewHolder>() {

    private var linkers: List<Linker> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListPerson(listLink: List<Linker>) {
        linkers = listLink
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPersonViewHolder {
        return ListPersonViewHolder(
            ItemPersonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListPersonViewHolder, position: Int) {

        val person = linkers.getOrNull(position)!!.person
        person?.let{
            //Set Person name
            holder.binding.personName.text = person.nameRu ?: ""
//Set Person job.
            holder.binding.personJob.text = person.professionText
//Load small poster. Before load image, show waiting animation
            val animationCard = LoadImageURLShow()
            animationCard.setAnimation(holder.binding.poster,person.posterUrl, R.dimen.card_film_radius)
//Set action on click item recyclerView
            holder.binding.root.setOnClickListener {
                onClick(person)
            }
        }
    }

    override fun getItemCount() = linkers.size
}

class ListPersonViewHolder(val binding: ItemPersonListBinding) :
    RecyclerView.ViewHolder(binding.root)
