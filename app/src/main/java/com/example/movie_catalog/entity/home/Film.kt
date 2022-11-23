package com.example.movie_catalog.entity.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val kinopoiskId:Int? = null,
    val filmId:Int? = null,
    val imdbId:String? = null,
    var nameRu:String? = null,
    val nameEn:String? = null,
    val rating:String? = null,
    var posterUrlPreview:String? = null,
    val countries:List<Country> = emptyList(),
    val genres:List<Genre> = emptyList()
): Parcelable
