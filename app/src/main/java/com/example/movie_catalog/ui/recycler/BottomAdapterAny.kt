package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.databinding.ItemBottomRecyclerBinding
import com.example.movie_catalog.databinding.ItemBottomRecyclerTextBinding
import com.example.movie_catalog.databinding.ItemBottomRecyclerYearsBinding
import com.example.movie_catalog.databinding.ItemProfileCardCollectionsBinding
import com.example.movie_catalog.entity.RecyclerData
import com.example.movie_catalog.entity.Collection
import javax.inject.Inject


class BottomAdapterAny @Inject constructor( private val onClick: (Any) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Any> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_profile_card_collections -> CollectionVH( ItemProfileCardCollectionsBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_bottom_recycler -> BottomAnyVH( ItemBottomRecyclerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_bottom_recycler_years -> BottomYearsVH(ItemBottomRecyclerYearsBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.item_bottom_recycler_text -> BottomTextVH(ItemBottomRecyclerTextBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CollectionDB -> R.layout.item_bottom_recycler
            is RecyclerData -> R.layout.item_bottom_recycler_years
            is Collection -> R.layout.item_profile_card_collections
            else -> R.layout.item_bottom_recycler_text
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.getOrNull(position)

        when (holder){
            is BottomAnyVH -> {
                if (item is CollectionDB){
                    holder.binding.checkBox.text = item.collection?.name ?: ""
                    holder.binding.tvCount.text = item.collection?.count.toString()
                    holder.binding.checkBox.isChecked = item.collection?.included ?: false
                    holder.binding.checkBox.setOnClickListener { onClick(item) }
                }
            }
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
            is CollectionVH -> {
                if (item is Collection){
                    holder.binding.tvNameCollection.text = item.name
                    holder.binding.tvCount.text = item.count.toString()
                    holder.binding.iv1.setImageResource(item.image)
                    holder.binding.ivDelCollection.setOnClickListener { onClick(item) }
                }
            }
        }
    }
    override fun getItemCount() = items.size
}

class BottomAnyVH(val binding: ItemBottomRecyclerBinding) : RecyclerView.ViewHolder(binding.root)
class BottomTextVH(val binding: ItemBottomRecyclerTextBinding) : RecyclerView.ViewHolder(binding.root)
class BottomYearsVH(val binding: ItemBottomRecyclerYearsBinding) : RecyclerView.ViewHolder(binding.root)
class CollectionVH(val binding: ItemProfileCardCollectionsBinding) : RecyclerView.ViewHolder(binding.root)