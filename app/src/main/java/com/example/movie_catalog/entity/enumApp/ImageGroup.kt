package com.example.movie_catalog.entity.enumApp

import androidx.annotation.StringRes
import com.example.movie_catalog.R

enum class ImageGroup (@StringRes val nameDisplay: Int) {
    STILL(R.string.STILL),
    SHOOTING(R.string.SHOOTING),
    POSTER(R.string.POSTER),
    FAN_ART(R.string.FAN_ART),
    PROMO(R.string.PROMO),
    CONCEPT(R.string.CONCEPT),
    WALLPAPER(R.string.WALLPAPER),
    COVER(R.string.COVER),
    SCREENSHOT(R.string.SCREENSHOT);
}