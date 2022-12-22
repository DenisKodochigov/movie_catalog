package com.example.movie_catalog.data.room

import androidx.room.Entity

@Entity(tableName = "crossFC", primaryKeys = ["idFilm", "idCollection"])
data class CrossFileCollection(
    val idFilm: Int,
    val idCollection:String,
    var included: Int
)
