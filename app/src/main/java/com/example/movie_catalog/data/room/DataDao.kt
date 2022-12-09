package com.example.movie_catalog.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/* Data access object to query the database. */
@Dao
interface DataDao {

    @Insert(entity = FilmDB::class)
    fun insertAll(vararg data: FilmDB)

    @Query("DELETE FROM films")
    fun nukeTable()

//    @Query("SELECT * FROM films WHERE filmId = :id")
//    fun selectLogById(id: Long): Cursor?

    @Query("SELECT * FROM films ORDER BY filmId DESC")
    fun getAll(): FilmDB

    @Query("SELECT * FROM films WHERE filmId = :id")
    fun getFilm(id: Int): FilmDB

    @Update
    fun update(film: FilmDB)

    @Query("SELECT viewed FROM films WHERE filmId = :id")
    fun getViewed(id: Int): Boolean

    @Query("SELECT favorite FROM films WHERE filmId = :id")
    fun getFavorite(id: Int): Boolean

    @Query("SELECT bookmark FROM films WHERE filmId = :id")
    fun getBookmark(id: Int): Boolean

    @Query("SELECT viewed FROM films WHERE filmId = :id")
    fun setViewed(id: Int): Boolean

    @Query("SELECT favorite FROM films WHERE filmId = :id")
    fun setFavorite(id: Int): Boolean

    @Query("SELECT bookmark FROM films WHERE filmId = :id")
    fun setBookmark(id: Int): Boolean
}
