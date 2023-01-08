package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.entity.enumApp.ImageGroup

data class ImageFilm(
    val imageUrl: String? = null,
    val previewUrl: String? = null,
    val imageGroup: ImageGroup? = null,
)