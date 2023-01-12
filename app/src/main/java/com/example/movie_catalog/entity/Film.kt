package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.home.seasons.SeasonDTO
import com.example.movie_catalog.entity.enumApp.TypeFilm
import com.example.movie_catalog.entity.filminfo.ImageFilm

data class Film(
    val filmId: Int? = null,
    var imdbId: String? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var rating: Double? = null, // = ratingKinopoisk:Double? = null,
    var posterUrlPreview: String? = null,
    var countries: List<Country>? = emptyList(),
    var genres: List<Genre> = emptyList(),
    var favorite: Boolean = false,
    var viewed: Boolean = false,
    var bookmark: Boolean = false,
    var professionKey: String? = null,
    var startYear: Int? = null,
    var images: MutableList<ImageFilm> = mutableListOf(),
    var posterUrl:String? = null,
    var logoUrl:String? = null,
    var nameOriginal:String? = null,
    var ratingImdb:Double? = null,
    var ratingAwait:Double? = null,
    var ratingGoodReview:Double? = null,
    var year:Int? = null,
    var totalSeasons: Int? =null,
    var listSeasons:List<SeasonDTO>? = null,
    var description:String? = null,
    var shortDescription:String? = null
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
