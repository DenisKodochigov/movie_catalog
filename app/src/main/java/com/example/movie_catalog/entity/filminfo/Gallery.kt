package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.App
import com.example.movie_catalog.R

data class Gallery(
    var tabs: List<Tab> = listOf(
        Tab("STILL", App.context.getString(R.string.STILL), null),
        Tab("SHOOTING", App.context.getString(R.string.SHOOTING), null),
        Tab("POSTER", App.context.getString(R.string.POSTER), null),
        Tab("FAN_ART", App.context.getString(R.string.FAN_ART), null),
        Tab("PROMO", App.context.getString(R.string.PROMO), null),
        Tab("CONCEPT", App.context.getString(R.string.CONCEPT), null),
        Tab("WALLPAPER", App.context.getString(R.string.WALLPAPER), null),
        Tab("COVER", App.context.getString(R.string.COVER), null),
        Tab("SCREENSHOT", App.context.getString(R.string.SCREENSHOT), null),
    )
)