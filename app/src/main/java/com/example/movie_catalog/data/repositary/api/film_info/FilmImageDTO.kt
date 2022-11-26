package com.example.movie_catalog.data.repositary.api.film_info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmImageDTO(
    @Json(name = "staffId") val staffId:Int? = null,
    @Json(name = "totalPages") val totalPages:Int? = null,
    @Json(name = "items") val items: List<FilmImageUrlDTO> = emptyList()
)
