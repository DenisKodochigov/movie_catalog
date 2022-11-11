package com.example.movie_catalog.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listgenres(
    @Json(name = "genres") val genres: List<String>? = emptyList(),
    @Json(name = "countries") val countries: List<String>? = emptyList()
)
