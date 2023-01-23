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
    //Insert new record to the table films
    @Insert(entity = FilmDB::class)
    fun insert(vararg data: FilmDB)
    //Updating a record in the table films
    @Update
    fun update(film: FilmDB)
    //Clearing the table films
    @Query("DELETE FROM films")
    fun nukeTable()
    //Select all entries and sort by id
    @Query("SELECT * FROM films ORDER BY idFilm DESC")
    fun getAll(): FilmDB
    //Select filmDB bi id
    @Query("SELECT * FROM films WHERE idFilm = :id")
    fun getFilm(id: Int): FilmDB?
    //Select all entries from films
    @Query("SELECT * FROM films")
    fun getFilms(): List<FilmDB>?
    //Get the VIEWED parameter for recording with id = id
    @Query("SELECT viewed FROM films WHERE idFilm = :id")
    fun getViewed(id: Int): Boolean
    //Select list value filmId from the table films where viewed = :viewed
    @Query("SELECT filmId FROM films WHERE viewed = :viewed")
    fun getViewedFilms(viewed: Boolean): List<Int>
    //Setting the viewed parameter in all records
    @Query("UPDATE films SET viewed = :value ")
    fun setAllViewed(value: Boolean)
    //Creating a stream to a movie in viewed state
    @Query("SELECT viewed FROM films WHERE idFilm = :id")
    fun setViewedFlow(id: Int): Flow<Boolean>
    //Delete record in the table films where id=id
    @Query("DELETE FROM films WHERE idFilm = :id")
    fun deleteByIdFilmDB(id: Int)
    //Selection of records from the FILES table where shs belong to the list
    @Query("SELECT * FROM films WHERE idFilm = :listId")
    fun getFilmInList(listId: List<Int>): List<FilmDB>

//Table COLLECTIONS
    //Insert new record to the table collections
    @Insert(entity = CollectionDB::class)
    fun insert(vararg data: CollectionDB)
    //Delete record in the table collections where id=id
    @Query("DELETE FROM collections WHERE idCollection = :id")
    fun deleteByIdCollection(id:Int)
    //Select all records from the table collections
    @Query("SELECT * FROM collections ")
    fun getCollections(): List<CollectionDB>
    //Selecting a record from a table collections where name =
    @Query("SELECT * FROM collections WHERE name = :collectionName ")
    fun getCollectionRecord(collectionName: String): CollectionDB?

//Table COLLECTIONS WITH FILM
    //Insert new record to the table CrossFC
    @Insert(entity = CrossFC::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: CrossFC)
    //Updating a record in the table CrossFC
    @Update
    fun update(data: CrossFC)
    // FAVORITE
    //Creating a stream to include a movie in the favorites collection
    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
    fun setFavoriteFlow(id: Int): Flow<Boolean>
    //Request for the film to belong to the favorites collection
    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
    fun getFavorite(id: Int): Boolean
    //BOOKMARK
    //Creating a stream to include a movie in the bookmark collection
    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
    fun setBookmarkFlow(id: Int): Flow<Boolean>
    //Request for the film to belong to the bookmark collection
    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
    fun getBookmark(id: Int): Boolean
    //Selecting a count of movies added to the collection
    @Query("SELECT * FROM crossFC WHERE collection_id = :id")
    fun getCountFilmCollection(id: Int): List<CrossFC>
    //Request an entry from the table crossFC for the movie belonging to the collection
    @Query("SELECT id FROM crossFC WHERE collection_id = :collectionId AND film_id = :filmId")
    fun getFilmInCollection( filmId: Int,collectionId: Int): Int?
    //Request for the film to belong to the collection
    @Query("SELECT 1 FROM crossFC WHERE film_id = :filmId")
    fun existFilmInCollections( filmId: Int): Boolean
    //Deleting record in table crossFC by id
    @Query("DELETE FROM crossFC WHERE id = :id")
    fun deleteByIdCrossFC(id:Int)
    //Clear all link collection to film for collection
    @Query("DELETE FROM crossFC WHERE collection_id = :id")
    fun clearByCollectionIdCrossFC(id:Int)
    //Selecting a list of movies-id added to the collection
    @Query("SELECT film_id FROM crossFC WHERE collection_id = :collectionId")
    fun getListFilmsInCollection(collectionId: Int): List<Int>
}
