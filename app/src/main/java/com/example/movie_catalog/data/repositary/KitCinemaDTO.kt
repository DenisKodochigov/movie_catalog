package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.entity.Kitcinema
import com.example.movie_catalog.entity.ListKitcinema
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class KitCinemaDTO(
    @Json(name = "photos") override val kitscinema: List<Kitcinema> = listOf(Kitcinema())
) : ListKitcinema


