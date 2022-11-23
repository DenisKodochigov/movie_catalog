package com.example.movie_catalog.entity.home

data class FilmDTO(
    val kinopoiskId:Int? = null,
    val filmId:Int? = null,
    var nameRu:String? = null,
    val nameEn:String? = null,
    val rating:Double? = null,
    var posterUrlPreview:String? = null,
    val countries:List<Country> = emptyList(),
    val genres:List<Genre> = emptyList()
)
