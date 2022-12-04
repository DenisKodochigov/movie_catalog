package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.repositary.api.film_info.FilmInfoDTO
import com.example.movie_catalog.data.repositary.api.home.seasons.SeasonsDTO

data class InfoFilmSeasons(
    var filmInfoDTO: FilmInfoDTO? = null,
    var seasonsDTO: SeasonsDTO? = null
)
