package com.example.movie_catalog.data.api.home.filter

import com.example.movie_catalog.entity.home.Country
import com.example.movie_catalog.entity.home.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterFilmDTO(
    @Json(name = "kinopoiskId") val kinopoiskId:Int? = null,
    @Json(name = "imdbId") val imdbId:String? = null,
    @Json(name = "nameRu") var nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "nameOriginal") val nameOriginal:String? = null,
    @Json(name = "countries") val countries:List<Country> = emptyList(),
    @Json(name = "genres") val genres:List<Genre> = emptyList(),
    @Json(name = "ratingKinopoisk") val ratingKinopoisk:Double? = null,
    @Json(name = "ratingImdb") val ratingImdb:Double? = null,
    @Json(name = "year") val year:Int? = null,
    @Json(name = "type") val type:String? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "posterUrlPreview") var posterUrlPreview:String? = null
)