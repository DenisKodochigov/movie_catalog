package com.example.movie_catalog.ui.recycler

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.ItemImageRecyclerBinding
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.filminfo.ImageFilm
import javax.inject.Inject

class ImagesAdapter @Inject constructor(private val onClick: () -> Unit,
    private val mode: Int = 0): RecyclerView.Adapter<ImagesVH>() {
    private var images: List<ImageFilm> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(imageList:List<ImageFilm>){
        images = imageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesVH {
        return ImagesVH( ItemImageRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return if (images.size > Constants.HOME_QTY_FILMCARD - 1 && mode == 0) {
            Constants.HOME_QTY_FILMCARD
        }
        else {
            images.size
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImagesVH, position: Int) {
//        Log.d("KDS", "ImagesAdapter, onBindViewHolder start. position=$position")
        val animationCard = LoadImageURLShow()
        holder.binding.image.foregroundGravity = Gravity.CENTER_HORIZONTAL
        with(holder.binding) {
            if (mode == 0){
                image.layoutParams.width = root.resources.getDimension(R.dimen.card_gallery_icon_width).toInt()
                image.layoutParams.height = root.resources.getDimension(R.dimen.card_gallery_icon_height).toInt()
                image.updateLayoutParams<LinearLayout.LayoutParams> {
                    marginStart = root.resources.getDimension(R.dimen.film_page_gallery_margin_hor).toInt()
                    marginEnd = root.resources.getDimension(R.dimen.film_page_gallery_margin_hor).toInt()
                }
                root.layoutParams.width = LayoutParams.WRAP_CONTENT
                animationCard.setAnimation( image, images[position].previewUrl!!, R.dimen.gallery_list_small_card_radius)
            } else {
                root.layoutParams.width = LayoutParams.MATCH_PARENT
                if (position % 3 == 2) {
                    image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_big_card_width).toInt()
                    image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_big_card_height).toInt()
                    root.gravity = Gravity.CENTER_HORIZONTAL
                    animationCard.setAnimation( image, images[position].imageUrl!!, R.dimen.gallery_list_big_card_radius)
                } else if (position % 3 == 1) {
                    image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                    image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                    root.gravity = Gravity.START
                    animationCard.setAnimation(image, images[position].previewUrl!!, R.dimen.gallery_list_small_card_radius)
                } else if (position % 3 == 0) {
                    image.layoutParams.width = root.resources.getDimension(R.dimen.gallery_list_small_card_width).toInt()
                    image.layoutParams.height = root.resources.getDimension(R.dimen.gallery_list_small_card_height).toInt()
                    root.gravity = Gravity.END
                    animationCard.setAnimation( image, images[position].previewUrl!!, R.dimen.gallery_list_small_card_radius)
                }
                image.updateLayoutParams<LinearLayout.LayoutParams> {
                    marginStart = root.resources.getDimension(R.dimen.gallery_list_small_card_margin_hor).toInt()
                    marginEnd = root.resources.getDimension(R.dimen.gallery_list_small_card_margin_hor).toInt()
                }
            }

            image.setOnClickListener {
                onClick()
            }
        }
    }
}

class ImagesVH(val binding: ItemImageRecyclerBinding) : RecyclerView.ViewHolder(binding.root)