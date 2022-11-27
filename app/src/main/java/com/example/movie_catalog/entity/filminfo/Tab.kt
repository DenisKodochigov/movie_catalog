package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.repositary.api.film_info.FilmImageDTO

data class Tab(
    var nameTab:String? = null,
    var nameTabDisplay:String? = null,
    var imagesUrl: FilmImageDTO? = null
)
