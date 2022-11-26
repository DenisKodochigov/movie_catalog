package com.example.movie_catalog.entity

import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO

data class Images (
    val tabs:List<Tab> = emptyList(),
    val imagesUrl: List<FilmImageUrlDTO> = emptyList()
)