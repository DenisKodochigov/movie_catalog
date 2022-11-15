package com.example.movie_catalog.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season (
    @Json(name = "number") val number:Int? = null,
    @Json(name = "episodes") val episodes:List<Episode>? = null,
)