package com.example.movie_catalog.data.repositary.api.home.top

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilms(
    @Json(name = "pagesCount") val pagesCount: Int? = null,
    @Json(name = "films") val films: MutableList<TopFilmDTO> = mutableListOf()
)
