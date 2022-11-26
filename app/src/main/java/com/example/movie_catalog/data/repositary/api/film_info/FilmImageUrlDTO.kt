package com.example.movie_catalog.data.repositary.api.film_info

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FilmImageUrlDTO(
    @Json(name = "imageUrl") val imageUrl:String? = null,
    @Json(name = "previewUrl") val previewUrl:String? = null
): Parcelable
