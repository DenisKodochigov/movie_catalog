package com.example.movie_catalog.entity

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
    var tabs: MutableList<FilmographyTab> = mutableListOf(),
    var films: MutableList<Film> = mutableListOf()
)