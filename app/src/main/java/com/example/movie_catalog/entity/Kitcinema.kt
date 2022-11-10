package com.example.movie_catalog.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Kitcinema (
        @Json(name = "name") val name: String? = null
) : Parcelable

