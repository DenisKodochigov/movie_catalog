package com.example.movie_catalog.data.api.home.filter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterDTO(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "totalPages") var totalPages:Int? = null,
    @Json(name = "items") var items:List<FilterFilmDTO>? = null,
)
