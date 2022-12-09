package com.example.movie_catalog.data.api.film_info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimilarItemDTO(
    @Json(name = "filmId") val filmId:Int? = null,
    @Json(name = "nameRu") val nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "nameOriginal") val nameOriginal:String? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "posterUrlPreview") val posterUrlPreview:String? = null,
    @Json(name = "relationType") val relationType:String? = null,
)
