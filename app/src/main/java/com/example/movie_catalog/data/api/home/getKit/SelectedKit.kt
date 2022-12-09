package com.example.movie_catalog.data.api.home.getKit

data class SelectedKit(
    var genre1: GenreIdDTO = GenreIdDTO(),
    var country1: CountryIdDTO = CountryIdDTO(),
    var genre2: GenreIdDTO = GenreIdDTO(),
    var country2: CountryIdDTO = CountryIdDTO(),
)
