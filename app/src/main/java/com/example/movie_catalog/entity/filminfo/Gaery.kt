package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO

data class Galery(
    var tabs: MutableList<TabsImage> = mutableListOf(),
    var listImageUrl: MutableList<FilmImageUrlDTO> = mutableListOf(),
    var viewingPosition:Int? = null,
    var listImage: MutableList<Images> = mutableListOf()
)