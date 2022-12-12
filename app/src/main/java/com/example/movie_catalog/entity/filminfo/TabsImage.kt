package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.api.film_info.FilmImageDTO

data class TabsImage(
    var tab: TabImage? = null,
    var imagesUrl: FilmImageDTO? = null
)
