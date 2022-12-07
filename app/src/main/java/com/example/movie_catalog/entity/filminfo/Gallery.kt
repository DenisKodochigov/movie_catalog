package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO

data class Gallery(
    var tabs: MutableList<TabImage> = mutableListOf(),
    var listImageUrl: MutableList<FilmImageUrlDTO> = mutableListOf(),
    var viewingPosition:Int? = null
)