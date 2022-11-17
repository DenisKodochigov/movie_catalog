package com.example.movie_catalog.ui.test

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.movie_catalog.databinding.ItemRecyclerFragmentTestBinding
import com.example.movie_catalog.ui.placeholder.PlaceholderContent.PlaceholderItem


class MytestRecyclerViewAdapter( private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MytestRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecyclerFragmentTestBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        val animationCard = holder.imageView.background as AnimationDrawable
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemRecyclerFragmentTestBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val imageView: ImageView = binding.poster

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}