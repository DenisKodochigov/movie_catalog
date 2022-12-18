package com.example.movie_catalog.entity

import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ProfKey

data class Linker(
    var film: Film? = null,
    var similarFilm: Film? = null,
    var person: Person? = null,
    var profKey: ProfKey? = null,
    var kit: Kit? = null,
)