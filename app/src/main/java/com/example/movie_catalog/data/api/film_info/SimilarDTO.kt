package com.example.movie_catalog.data.api.film_info

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimilarDTO(
    @Json(name = "total") val total:Int? = null,
    @Json(name = "items") val items:List<SimilarItemDTO>? = null,
)