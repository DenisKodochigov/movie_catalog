package com.example.movie_catalog.data.api.home.seasons

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonsDTO(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:List<SeasonDTO>? = null
)
