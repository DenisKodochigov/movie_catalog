package com.example.movie_catalog.entity

import com.example.movie_catalog.entity.enumApp.ProfKey

data class FilmographyData (
    var linkers: List<Linker> = emptyList(),
    var tabsKey: List<Pair<ProfKey?, Int>> = emptyList()
)