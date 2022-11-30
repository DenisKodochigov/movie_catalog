package com.example.movie_catalog.entity

import com.example.movie_catalog.data.repositary.api.person.PersonFilmDTO
import com.squareup.moshi.Json

data class Person(
    val personId:Int? = null,
    val webUrl:String? = null,
    val nameRu:String? = null,
    val nameEn:String? = null,
    val sex:String? = null,
    val posterUrl:String? = null,
    val growth:Int? = null,
    val birthday:String? = null,
    val death:String? = null,
    val age:String? = null,
    val birthplace:String? = null,
    val deathplace:String? = null,
    val spouses:List<String> = emptyList(),
    val hasAwards:Int? = null,
    val profession:String? = null,
    val facts:List<String> = emptyList(),
    val films:List<Film> = emptyList(),
)
