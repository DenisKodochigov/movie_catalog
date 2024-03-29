package com.example.movie_catalog.data.api.film_info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
//
@JsonClass(generateAdapter = true)
data class FilmImageDTO(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "totalPages") val totalPages:Int? = null,
    @Json(name = "items") var items: MutableList<FilmImageUrlDTO> = mutableListOf()
)
