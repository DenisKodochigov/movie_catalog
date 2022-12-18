package com.example.movie_catalog.entity

import com.example.movie_catalog.entity.enumApp.ImageGroup
import com.example.movie_catalog.entity.filminfo.ImageFilm

data class Gallery (
    var images: List<ImageFilm> = emptyList(),
    var tabs: List<Pair<ImageGroup?, Int>> = emptyList()
)