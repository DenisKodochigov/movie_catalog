package com.example.movie_catalog.data.api.home.getKit

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryIdDTO(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "country") val country: String? = null
) : Parcelable