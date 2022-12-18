package com.example.movie_catalog.entity.enumApp

import com.example.movie_catalog.App
import com.example.movie_catalog.R

enum class ImageGroup (val nameDisplay: String) {
    STILL(App.context.getString(R.string.STILL)),
    SHOOTING(App.context.getString(R.string.SHOOTING)),
    POSTER(App.context.getString(R.string.POSTER)),
    FAN_ART(App.context.getString(R.string.FAN_ART)),
    PROMO(App.context.getString(R.string.PROMO)),
    CONCEPT(App.context.getString(R.string.CONCEPT)),
    WALLPAPER(App.context.getString(R.string.WALLPAPER)),
    COVER(App.context.getString(R.string.COVER)),
    SCREENSHOT(App.context.getString(R.string.SCREENSHOT)),
}