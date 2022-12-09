package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.api.film_info.FilmImageDTO

data class TabImage(
    var tab: Tab? = null,
    var imagesUrl: FilmImageDTO? = null
)
