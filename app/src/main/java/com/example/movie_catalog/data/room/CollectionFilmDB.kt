package com.example.movie_catalog.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionFilmDB (
    @PrimaryKey val name: String,
    var included: Boolean = false,
    var count: Int = 0,
    val filmId: Int = 0
)