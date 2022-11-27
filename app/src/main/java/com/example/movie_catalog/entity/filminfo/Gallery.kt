package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.R

data class Gallery(
    var tabs: List<Tab> = listOf(
        Tab("FAN_ART", (R.string.FAN_ART).toString(), null),
        Tab("CONCEPT", (R.string.CONCEPT).toString(), null),
        Tab("COVER", (R.string.STILL).toString(), null),
        Tab("POSTER", (R.string.POSTER).toString(), null),
        Tab("PROMO", (R.string.PROMO).toString(), null),
        Tab("SCREENSHOT", (R.string.STILL).toString(), null),
        Tab("SHOOTING", (R.string.SHOOTING).toString(), null),
        Tab("STILL", (R.string.STILL).toString(), null),
        Tab("WALLPAPER", (R.string.WALLPAPER).toString(), null),
    )
)