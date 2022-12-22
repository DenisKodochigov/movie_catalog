package com.example.movie_catalog.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/* Data access object to query the database. */
@Dao
interface DataDao {

    @Insert(entity = FilmDB::class)
    fun insert(vararg data: FilmDB)

    @Query("DELETE FROM films")
    fun nukeTable()

    @Query("SELECT * FROM films ORDER BY filmId DESC")
    fun getAll(): FilmDB

    @Query("SELECT * FROM films WHERE filmId = :id")
    fun getFilm(id: Int): FilmDB?

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

    @Query("SELECT * FROM collections ")
    fun getCollection(): List<CollectionFilmDB>

    @Insert(entity = CollectionFilmDB::class)
    fun insert(vararg data: CollectionFilmDB)

    @Query("SELECT * FROM crossFC WHERE idCollection = :id AND included = :included")
    fun getCountFilmCollection(id: String, included:Int): List<CrossFileCollection>

    @Query("SELECT * FROM crossFC WHERE idCollection = :collectionId AND idFilm = :filmId")
    fun getFilmInCollection(collectionId: String, filmId: Int): CrossFileCollection?

    @Update
    fun update(crossFileCollection: CrossFileCollection)
    @Insert(entity = CrossFileCollection::class)
    fun insert(vararg data: CrossFileCollection)
}
