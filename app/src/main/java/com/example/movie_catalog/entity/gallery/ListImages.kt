package com.example.movie_catalog.entity.gallery

data class ListImages(
    val images: MutableList<String> = mutableListOf(),
    var position: Int? = null
)
