package com.example.movie_catalog.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "filmId") var filmId: Long = 0,
    @ColumnInfo(name = "msg") val msg: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "viewed") val viewed:Boolean,
    @ColumnInfo(name = "bookmark") val bookmark: Boolean,
    @ColumnInfo(name = "favorite") val favorite: Boolean
)