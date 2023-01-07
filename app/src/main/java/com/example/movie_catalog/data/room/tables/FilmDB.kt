package com.example.movie_catalog.data.room.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movie_catalog.entity.Film

@Entity(tableName = "films")
@TypeConverters(ConverterForFilmDB::class)
data class FilmDB(
    @PrimaryKey(autoGenerate = true) var idFilm: Int,
    val msg: String,
    @Embedded var film: Film?
)
