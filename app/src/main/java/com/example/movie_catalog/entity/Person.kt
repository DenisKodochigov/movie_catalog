package com.example.movie_catalog.entity

import com.example.movie_catalog.entity.enumApp.ProfKey

data class Person(
    var personId:Int? = null,
    var nameRu:String? = null,
    var nameEn:String? = null,
    var posterUrl:String? = null,
    var age:String? = null,
    var hasAwards:Int? = null,
    var profession:String? = null,
    val professionKey:String? = null,
    val professionText:String? = null,
    val description:String? = null,
    var tabs: MutableList<ProfKey> = mutableListOf(),
    var films: MutableList<Film> = mutableListOf()
)