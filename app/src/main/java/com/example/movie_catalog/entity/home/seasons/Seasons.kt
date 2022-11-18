package com.example.movie_catalog.entity.home.seasons

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Seasons(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:List<Season>? = null
)
