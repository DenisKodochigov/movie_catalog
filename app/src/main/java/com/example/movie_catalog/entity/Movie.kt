package com.example.movie_catalog.entity

data class Movie(
    val id:Int? = null,
    val name:String? = null,
    val poster:String? = null,
    val genre:String? = null,
    val rating:Double? = null,
    val viewing:Boolean = false
)
