package com.example.movie_catalog.entity.home.premieres

import com.example.movie_catalog.entity.home.premieres.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Premieres(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:MutableList<Film> = mutableListOf()
)
