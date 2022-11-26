package com.example.movie_catalog.data.repositary.api.film_info

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PersonDTO(
    @Json(name = "staffId") val staffId:Int? = null,
    @Json(name = "nameRu") val nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "description") val description:String? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "professionText") val professionText:String? = null,
    @Json(name = "professionKey") val professionKey:String? = null,
): Parcelable
