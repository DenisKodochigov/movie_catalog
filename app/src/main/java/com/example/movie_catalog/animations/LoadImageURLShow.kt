package com.example.movie_catalog.animations

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class LoadImageURLShow {
    var animationCard = AnimationDrawable()

    fun setAnimation(view: ImageView, imageURL: String?){
        if (view.background != null) {
            animationCard = view.background as AnimationDrawable
        }

        if (imageURL == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        } else {
            Glide.with(view)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(view)
            animationCard.stop()
        }
    }
}