package com.example.movie_catalog.entity

import com.example.movie_catalog.entity.filminfo.Images
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.home.Country
import com.example.movie_catalog.entity.home.Genre

data class Film(
    val filmId: Int? = null,
    val imdbId: String? = null,
    var nameRu: String? = null,
    val nameEn: String? = null,
    val rating: String? = null,
    var posterUrlPreview: String? = null,
    val countries: List<Country>? = emptyList(),
    val genres: List<Genre> = emptyList(),
    var favorite: Boolean = false,
    var viewed: Boolean = false,
    var bookmark: Boolean = false,
    val professionKey: String? = null,
    val startYear: String? = null,
    val kit: Kit? = null,
    var similar: Int? = null,
    var images: MutableList<Images> = mutableListOf(),

) {

    fun genresTxt(): String {
        //Set film genres.
        var genreTxt = ""
        genres.forEach {
            genreTxt = if (genreTxt == "") {
                it.genre.toString()
            } else {
                genreTxt + ", " + it.genre.toString()
            }
        }
        return genreTxt
    }
}
