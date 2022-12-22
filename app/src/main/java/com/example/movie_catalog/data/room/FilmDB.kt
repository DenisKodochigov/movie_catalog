package com.example.movie_catalog.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmDB(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var filmId: Int,
    val msg: String,
    val timestamp: Long,
    var viewed:Boolean,
    var bookmark: Boolean,
    var favorite: Boolean,
)
