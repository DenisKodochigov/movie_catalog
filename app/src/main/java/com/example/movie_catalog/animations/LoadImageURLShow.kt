package com.example.movie_catalog.animations

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.movie_catalog.R
import javax.inject.Inject

/*
Module for downloading images from the Internet using glide and animation processing load.
During the loading process, the image changes size and context.
Performs exception handling.
Before loading the image, the gradient animation is displayed.
 */
class LoadImageURLShow  @Inject constructor() {
    var animationCard = AnimationDrawable()
    @Inject
    lateinit var cont: Context
    @SuppressLint("ResourceAsColor")
    fun setAnimation(view: ImageView, imageURL: String?, roundedCorners:Int, centerCrop:Boolean = true){

        val radius = view.resources.getDimensionPixelOffset(roundedCorners)
        // Start of the image loading animation.
        if (view.background != null) {
            animationCard = view.background as AnimationDrawable
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
//            Log.d("KDS","LoadImageURLShow.setAnimation animation start $imageURL")
        }
        //If there is no link to the image, then we substitute a stub and stop the animation.
        if (imageURL == ""){
            view.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            animationCard.stop()
        } else if (imageURL != null){
        //Starting the image loading process.
            //Options for transforming the image after loading - rounding the corners and centering in the center
            val option = if (!centerCrop) RequestOptions().transform(RoundedCorners(radius))
            else RequestOptions().transform(CenterCrop(), RoundedCorners(radius))
            Glide
                .with(view)
                .load(imageURL)
                    //If there is a loading error, we substitute a stub
                .fallback(R.drawable.ic_baseline_image_not_supported_24)
                .apply(option)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object: RequestListener<Drawable> {
                    //If an error occurs, we output a message
                    override fun onLoadFailed( resource: GlideException?, model: Any?,
                        target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Toast.makeText(cont,"Error load image",Toast.LENGTH_SHORT).show()
                        return false
                    }
                    //When loading successfully, we stop the animation
                    override fun onResourceReady(resource: Drawable?, model: Any?,
                        target: Target<Drawable>?, dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean): Boolean {
                        animationCard.stop()
                        view.background?.alpha = (0)
//                        Log.d("KDS","LoadImageURLShow.setAnimation animation stop $imageURL")
                        return false
                    }
                })
                .into(view)
        }
    }
}