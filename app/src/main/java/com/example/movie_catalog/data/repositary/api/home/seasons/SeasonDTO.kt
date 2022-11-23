package com.example.movie_catalog.data.repositary.api.home.seasons

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonDTO (
    @Json(name = "number") val number:Int? = null,
    @Json(name = "episodes") val episodes:List<EpisodeDTO>? = null,
)