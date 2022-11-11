package com.example.movie_catalog.entity

data class Film(
    val id:Int? = null,
    val name:String? = null,
    val poster:String? = null,
    val genre:String? = null,
    val rating:Double? = null,
    val viewing:Boolean = false
)
