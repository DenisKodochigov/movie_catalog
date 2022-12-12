package com.example.movie_catalog.entity.filminfo

data class Gallery (
    var tabs: MutableList<TabImage> = mutableListOf(),
    var listImage: MutableList<Images> = mutableListOf()
)