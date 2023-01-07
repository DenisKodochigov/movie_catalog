package com.example.movie_catalog.data.room

import androidx.room.*
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.tables.CrossFC
import com.example.movie_catalog.data.room.tables.FilmDB
import kotlinx.coroutines.flow.Flow

/* Data access object to query the database. */
@Dao
interface DataDao {
//table FILMS
    @Insert(entity = FilmDB::class)
    fun insert(vararg data: FilmDB)

    @Update
    fun update(film: FilmDB)

    @Query("DELETE FROM films")
    fun nukeTable()

    @Query("SELECT * FROM films ORDER BY idFilm DESC")
    fun getAll(): FilmDB

    @Query("SELECT * FROM films WHERE idFilm = :id")
    fun getFilm(id: Int): FilmDB?

    @Query("SELECT viewed FROM films WHERE idFilm = :id")
    fun getViewed(id: Int): Boolean
    @Query("SELECT viewed FROM films WHERE idFilm = :id")
    fun setViewedFlow(id: Int): Flow<Boolean>
    @Query("DELETE FROM films WHERE idFilm = :id")
    fun deleteByIdFilmDB(id: Int)

//Table COLLECTIONS
    @Insert(entity = CollectionDB::class)
    fun insert(vararg data: CollectionDB)

    @Query("DELETE FROM collections WHERE idCollection = :id")
    fun deleteById(id:Int)

    @Query("SELECT * FROM collections ")
    fun getCollection(): List<CollectionDB>

    @Query("SELECT * FROM collections WHERE name = :collectionName ")
    fun getCollectionRecord(collectionName: String): CollectionDB?

//Table COLLECTIONS WITH FILM
    @Insert(entity = CrossFC::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: CrossFC)

    @Update
    fun update(data: CrossFC)
    // FAVORITE
    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
    fun setFavoriteFlow(id: Int): Flow<Boolean>

    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
    fun getFavorite(id: Int): Boolean
    //BOOKMARK
    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
    fun setBookmarkFlow(id: Int): Flow<Boolean>

    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
    fun getBookmark(id: Int): Boolean

    @Query("SELECT * FROM crossFC WHERE collection_id = :id")
    fun getCountFilmCollection(id: Int): List<CrossFC>

    @Query("SELECT id FROM crossFC WHERE collection_id = :collectionId AND film_id = :filmId")
    fun getFilmInCollection( filmId: Int,collectionId: Int): Int?

    @Query("SELECT 1 FROM crossFC WHERE film_id = :filmId")
    fun existFilmInCollections( filmId: Int): Boolean

    @Query("DELETE FROM crossFC WHERE id = :id")
    fun deleteByIdCrossFC(id:Int)


}
