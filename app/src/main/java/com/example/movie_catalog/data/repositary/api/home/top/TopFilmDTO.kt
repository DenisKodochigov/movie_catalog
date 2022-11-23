package com.example.movie_catalog.data.repositary.api.home.top

import com.example.movie_catalog.entity.home.Country
import com.example.movie_catalog.entity.home.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilmDTO(
    @Json(name = "filmId") val filmId:Int? = null,
    @Json(name = "nameRu") var nameRu:String? = null,
    @Json(name = "nameEn") val nameEn:String? = null,
    @Json(name = "year") val year:Int? = null,
    @Json(name = "filmLength") val filmLength:String? = null,
    @Json(name = "countries") val countries:List<Country> = emptyList(),
    @Json(name = "genres") val genres:List<Genre> = emptyList(),
    @Json(name = "rating") val rating:Double? = null,
    @Json(name = "ratingVoteCount") val ratingVoteCount:Int? = null,
    @Json(name = "posterUrl") val posterUrl:String? = null,
    @Json(name = "posterUrlPreview") var posterUrlPreview:String? = null,
    @Json(name = "ratingChange") val ratingChange:String? = null,
)
