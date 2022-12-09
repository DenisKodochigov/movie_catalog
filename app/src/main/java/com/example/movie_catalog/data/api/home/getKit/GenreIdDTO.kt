package com.example.movie_catalog.data.api.home.getKit

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GenreIdDTO(
    @Json(name = "id") val id:Int? = null,
    @Json(name = "genre") val genre:String?=null
) : Parcelable