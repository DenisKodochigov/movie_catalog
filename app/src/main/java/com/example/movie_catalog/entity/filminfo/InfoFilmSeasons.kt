package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.data.api.film_info.FilmInfoDTO
import com.example.movie_catalog.data.api.home.seasons.SeasonsDTO

data class InfoFilmSeasons(
    var infoFilm: FilmInfoDTO? = null,
    var infoSeasons: SeasonsDTO? = null
)
