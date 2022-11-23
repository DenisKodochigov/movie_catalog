package com.example.movie_catalog.data.repositary.api.home.getKit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListGenresDTO(
    @Json(name = "genres") val genres: List<GenreIdDTO>? = emptyList(),
    @Json(name = "countries") val countries: List<CountryIdDTO>? = emptyList()
)
