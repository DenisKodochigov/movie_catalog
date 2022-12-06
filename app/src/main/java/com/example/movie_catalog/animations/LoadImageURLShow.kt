package com.example.movie_catalog.animations

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movie_catalog.R

class LoadImageURLShow {
    var animationCard = AnimationDrawable()

    @SuppressLint("ResourceAsColor")
    fun setAnimation(view: ImageView, imageURL: String?, roundedCorners:Int){

        val radius = view.resources.getDimensionPixelOffset(roundedCorners)

        if (view.background != null) {
            animationCard = view.background as AnimationDrawable
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        }

        if (imageURL == ""){
            view.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            animationCard.stop()
        } else if (imageURL != null){
            Glide.with(view)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(view)
            animationCard.stop()
//            view.background.alpha = 0
        }
    }
}