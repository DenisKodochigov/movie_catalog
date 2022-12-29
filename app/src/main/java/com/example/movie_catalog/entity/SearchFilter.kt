package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm

data class SearchFilter(
    var typeFilm: TypeFilm? = null,
    var country: CountryIdDTO? = null,
    var genre: GenreIdDTO? = null,
    var year: Pair<Int, Int>? = null,
    var rating: Pair<Double, Double>? = null,
    var viewed: Boolean? = null,
    var sorting: SortingField? = null,
    var keyWord: String? = null,
)
