package com.example.movie_catalog.ui.list_person.recyclerListPerson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.databinding.ItemPersonListBinding
import com.example.movie_catalog.entity.Person
import javax.inject.Inject

class ListPersonAdapter @Inject constructor(
    private val onClick: (Person) -> Unit
) : RecyclerView.Adapter<ListPersonViewHolder>() {

    private var listPerson: List<Person> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListPerson(listPerson: List<Person>) {
        this.listPerson = listPerson
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPersonViewHolder {
        return ListPersonViewHolder(
            ItemPersonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListPersonViewHolder, position: Int) {

        val person = listPerson.getOrNull(position)!!
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

    override fun getItemCount() = listPerson.size
}

class ListPersonViewHolder(val binding: ItemPersonListBinding) :
    RecyclerView.ViewHolder(binding.root)
