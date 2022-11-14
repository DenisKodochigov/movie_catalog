package com.example.movie_catalog.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GenreID(
    @Json(name = "id") val id:Int? = null,
    @Json(name = "genre") val genre:String?=null
) : Parcelable