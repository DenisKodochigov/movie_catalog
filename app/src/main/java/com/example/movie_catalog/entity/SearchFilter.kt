package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm

data class SearchFilter(
    var typeFilm: TypeFilm = TypeFilm.FILM,
    var country: CountryIdDTO = CountryIdDTO(id = 1),
    var genre: GenreIdDTO = GenreIdDTO(id = 11),
    var year: Pair<Int, Int> = Pair(1900, 2000),
    var rating: Pair<Double, Double> = Pair(1.0, 10.0),
    var viewed: Boolean = false,
    var sorting: SortingField = SortingField.RATING,
    var keyWord: String = "",
)
