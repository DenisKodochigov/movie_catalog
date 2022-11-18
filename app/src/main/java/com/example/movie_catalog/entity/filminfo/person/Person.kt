package com.example.movie_catalog.entity.filminfo.person

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "staffId") val staffId:Int? = null,
    @Json(name = "nameRu") val nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "description") val description:String? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "professionText") val professionText:String? = null,
    @Json(name = "professionKey") val professionKey:String? = null,
)
