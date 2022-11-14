package com.example.movie_catalog.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListGenres(
    @Json(name = "genres") val genres: List<GenreID>? = emptyList(),
    @Json(name = "countries") val countries: List<CountryID>? = emptyList()
)
