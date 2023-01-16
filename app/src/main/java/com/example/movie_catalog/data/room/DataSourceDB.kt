package com.example.movie_catalog.data.room

import com.example.movie_catalog.App
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.tables.CrossFC
import com.example.movie_catalog.data.room.tables.FilmDB
import com.example.movie_catalog.entity.Film
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
/*
A class for generating queries to the database and their further processing.
 */
@Singleton
open class DataSourceDB  @Inject constructor(){

    private val dataDao = ProviderDao().getDataDao(App.context)
    //Select filmDB bi id
    fun getFilm(film: Film): FilmDB? {
        return dataDao.getFilm(film.filmId!!)
    }
    //Select all entries from films
    fun getFilms(): List<FilmDB>? {
        return dataDao.getFilms()
    }
    //Request for the film to belong to the collection. If the movie does not belong to any of
    // the collections and does not have the viewed attribute, then we delete it from the database
    private fun checkFilmInCollection(film: Film){
        if ( ! dataDao.existFilmInCollections(film.filmId!!)) {
            if (! dataDao.getViewed(film.filmId)) dataDao.deleteByIdFilmDB(film.filmId)
        }
    }
    private fun checkFilmInCollection(filmId: Int){
        if ( ! dataDao.existFilmInCollections(filmId)) {
            if (! dataDao.getViewed(filmId)) dataDao.deleteByIdFilmDB(filmId)
        }
    }
    //Insert new record to the table films
    fun addFilm(filmDB: FilmDB){
        dataDao.insert(filmDB)
    }
    //Creating a stream to a movie in viewed state
    fun viewedFlow(id: Int): Flow<Boolean> = dataDao.setViewedFlow(id)
    //Creating a stream to include a movie in the bookmark collection
    fun bookmarkFlow(id: Int): Flow<Boolean> = dataDao.setBookmarkFlow(id)
    //Creating a stream to include a movie in the favorites collection
    fun favoriteFlow(id: Int): Flow<Boolean> = dataDao.setFavoriteFlow(id)
    //Set value VIEWED for film
    fun setViewed(film: Film) {
        //We find the file in the films table
        val filmDB = dataDao.getFilm(film.filmId!!)
        if (filmDB == null) {
            //If the movie is not found, create a new record and set the viewed attribute to true
            val filmN = film.copy()
            filmN.viewed = true //Добавляем фмльм в базу, это возможно полсе того как поставили признак просмотрено
            addFilm(FilmDB(idFilm = filmN.filmId!!, msg = "", filmN))
        } else {
            //If the movie is found, set the viewed attribute to the opposite value
            filmDB.film?.let { it.viewed = !it.viewed}
            dataDao.update(filmDB)
        }
    }
    //Select all records from the table collections
    fun getCollections():List<CollectionDB>{
        return dataDao.getCollection()
    }
    //Insert new record to the table collections
    fun addCollection(collection: CollectionDB){
        dataDao.insert(collection)
    }
    //Selecting a count of movies added to the collection
    fun getCountFilmCollection(collectionId: Int): List<CrossFC>{
        return dataDao.getCountFilmCollection(collectionId)
    }
    //Adding (deleting) a movie to (from) a collection
    fun addRemoveFilmToCollection( film: Film, collectionId: Int){
        //If the movie is not in the database, add it.
        if (getFilm(film) == null) addFilm(FilmDB(idFilm = film.filmId!!, msg = "", film))
        //We check the connection of the movie with the collection
        val idCrossFc = dataDao.getFilmInCollection(film.filmId!!, collectionId)
        if (idCrossFc == null){
            //Linking a movie recording to a collection recording
            dataDao.insert(CrossFC( film_id = film.filmId, collection_id = collectionId, value = true ))
        } else {
            //Deleting link a movie recording to a collection recording
            dataDao.deleteByIdCrossFC(idCrossFc)
            //We check and, if necessary, delete the movie from the database
            checkFilmInCollection(film)
        }
    }
    //Request an entry from the table crossFC for the movie belonging to the collection
    fun getFilmInCollection(filmId: Int, collectionId: Int): Boolean{
        return dataDao.getFilmInCollection(filmId, collectionId) != null
    }
    //Selecting a record from a table collections where name =
    fun getCollectionRecord(nameCollection: String):CollectionDB? = dataDao.getCollectionRecord(nameCollection)
    //Select list value filmId from the table films where viewed = :viewed
    fun getViewedFilmsId() = dataDao.getViewedFilms(true)
    //Selecting a list of movies-id added to the collection
    fun getListFilmsIdInCollection(collectionId: Int) = dataDao.getListFilmsInCollection(collectionId)
    //Selection of records from the FILES table where shs belong to the list
    fun getListFilmDB(listId: List<Int>) = dataDao.getFilmInList(listId)
    //Setting the viewed parameter to false in all records
    fun clearViewedFilm(){
        dataDao.setAllViewed(false)
    }
    //Deleting record in table crossFC by bookmark
    fun clearBookmarkFilm(){
        dataDao.deleteByIdCrossFC(2)
    }
    //Deleting collection by name collection
    fun deleteCollection(nameCollection: String){
        val colDb = dataDao.getCollectionRecord(nameCollection)
        colDb?.let{
            //We do not delete the favorite and bookmark collections
            if (it.idCollection != 1 && it.idCollection != 2) {
                //Choosing movies from the collection.
                val listFilmId = dataDao.getListFilmsInCollection(it.idCollection)
                //Deleting links by collection
                dataDao.deleteByIdCrossFC(it.idCollection)
                //Deleting collection
                dataDao.deleteByIdCollection(it.idCollection)
                //Clearing the table: films
                listFilmId.forEach { id -> checkFilmInCollection(id) }
            }
        }
    }
}