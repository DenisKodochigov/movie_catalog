package com.example.movie_catalog.archiv

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.tables.CrossFC
import com.example.movie_catalog.data.room.tables.FilmDB

data class CollectionWithFilm(
    @Embedded
    val collection: CollectionDB,
    @Relation(
        parentColumn = "idCollection",
        entityColumn = "idFilm",
        associateBy = Junction(
            CrossFC::class,
            parentColumn = "collection_id",
            entityColumn = "film_id"
        )
    )
    val film: List<FilmDB>
)
