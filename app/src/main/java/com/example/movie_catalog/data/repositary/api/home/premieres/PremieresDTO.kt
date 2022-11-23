package com.example.movie_catalog.data.repositary.api.home.premieres

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PremieresDTO(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:MutableList<FilmDTO> = mutableListOf()
)
