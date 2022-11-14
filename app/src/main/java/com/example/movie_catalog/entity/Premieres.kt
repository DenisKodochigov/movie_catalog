package com.example.movie_catalog.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Premieres(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:MutableList<Film> = mutableListOf()
)
