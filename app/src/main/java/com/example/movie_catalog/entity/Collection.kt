package com.example.movie_catalog.entity

data class Collection(
    val name: String,
    var count: Int = 0,
    var image: Int = 0,
    var included: Boolean = false
)