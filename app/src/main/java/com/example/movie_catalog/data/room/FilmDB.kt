package com.example.movie_catalog.data.room

import androidx.room.ColumnInfo
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
    var favorite: Boolean
)


//@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
//@ColumnInfo(name = "filmId") var filmId: Int,
//@ColumnInfo(name = "msg") val msg: String,
//@ColumnInfo(name = "timestamp") val timestamp: Long,
//@ColumnInfo(name = "viewed") var viewed:Boolean,
//@ColumnInfo(name = "bookmark") var bookmark: Boolean,
//@ColumnInfo(name = "favorite") var favorite: Boolean