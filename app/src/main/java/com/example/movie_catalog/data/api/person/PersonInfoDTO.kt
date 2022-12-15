package com.example.movie_catalog.data.api.person

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonInfoDTO (
    @Json(name = "personId") val personId:Int? = null,
    @Json(name = "webUrl") val webUrl:String? = null,
    @Json(name = "nameRu") val nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "sex") val sex:String? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "growth") val growth:Int? = null,
    @Json(name = "birthday") val birthday:String? = null,
    @Json(name = "death") val death:String? = null,
    @Json(name = "age") val age:String? = null,
    @Json(name = "birthplace") val birthplace:String? = null,
    @Json(name = "deathplace") val deathplace:String? = null,
    @Json(name = "spouses") val spouses:List<String> = emptyList(),
    @Json(name = "hasAwards") val hasAwards:Int? = null,
    @Json(name = "profession") val profession:String? = null,
    @Json(name = "facts") val facts:List<String> = emptyList(),
    @Json(name = "films") val films:List<PersonFilmDTO> = emptyList(),
)