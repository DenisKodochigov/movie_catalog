package com.example.movie_catalog.entity.home.seasons

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "seasonNumber") val seasonNumber:Int? = null,
    @Json(name = "episodeNumber") val episodeNumber:Int? = null,
    @Json(name = "nameRu") val nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "synopsis") val synopsis:String? = null,
    @Json(name = "releaseDate") val releaseDate:String? = null,
)
