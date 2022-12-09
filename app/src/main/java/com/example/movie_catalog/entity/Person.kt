package com.example.movie_catalog.entity

data class Person(
    var personId:Int? = null,
    val nameRu:String? = null,
    val nameEn:String? = null,
    val posterUrl:String? = null,
    val age:String? = null,
    val hasAwards:Int? = null,
    val profession:String? = null,
    val films:List<Film> = emptyList(),
    val tabs: List<FilmographyTab> = emptyList()
) {
}
