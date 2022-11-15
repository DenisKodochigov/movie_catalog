package com.example.movie_catalog.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "genre") var genre:String?=null
): Parcelable