package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.repositary.api.home.seasons.SeasonsDTO

data class FilmInfoSeasons(
    var filmInfo: FilmInfo? = null,
    var seasonsDTO: SeasonsDTO? = null
)
