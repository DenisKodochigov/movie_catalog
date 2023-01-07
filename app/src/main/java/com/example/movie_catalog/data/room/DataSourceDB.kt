package com.example.movie_catalog.data.room

import com.example.movie_catalog.App
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.tables.CrossFC
import com.example.movie_catalog.data.room.tables.FilmDB
import com.example.movie_catalog.entity.Film
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DataSourceDB  @Inject constructor(){

    private val dataDao = ProviderDao().getDataDao(App.context)

    fun getViewed(id:Int):Boolean{
        return dataDao.getViewed(id)
    }
    fun getFilm(film: Film): FilmDB? {
        val filmDB = dataDao.getFilm(film.filmId!!)
        if (filmDB == null) {
            dataDao.insert(FilmDB(idFilm = film.filmId, msg = "", film))
        }
        return filmDB
    }
    private fun checkFilmInCollection(film: Film){
        if ( ! dataDao.existFilmInCollections(film.filmId!!)) {
            dataDao.deleteByIdFilmDB(film.filmId)
        }
    }
    fun addFilm(filmDB: FilmDB){
        dataDao.insert(filmDB)
    }
    fun viewedFlow(id: Int): Flow<Boolean> = dataDao.setViewedFlow(id)
    fun bookmarkFlow(id: Int): Flow<Boolean> = dataDao.setBookmarkFlow(id)
    fun favoriteFlow(id: Int): Flow<Boolean> = dataDao.setFavoriteFlow(id)

    fun setViewed(film: Film) {
        val filmDB = dataDao.getFilm(film.filmId!!)
        if (filmDB == null) {
            dataDao.insert(FilmDB(idFilm = film.filmId, msg = "", film))
        } else {
            filmDB.film?.let { it.viewed = !it.viewed}
            dataDao.update(filmDB)
        }
    }

    fun getCollections():List<CollectionDB>{
        return dataDao.getCollection()
    }

    fun addCollection(collection: CollectionDB){
        dataDao.insert(collection)
    }

    fun getCountFilmCollection(collectionId: Int): List<CrossFC>{
        return dataDao.getCountFilmCollection(collectionId)
    }

    fun addRemoveFilmToCollection( film: Film, collectionId: Int){
        getFilm(film)
        val idCrossFc = dataDao.getFilmInCollection(film.filmId!!, collectionId)
        if (idCrossFc == null){
            dataDao.insert(CrossFC( film_id = film.filmId, collection_id = collectionId, value = true ))
        } else {
            dataDao.deleteByIdCrossFC(idCrossFc)
            checkFilmInCollection(film)
        }
    }

    fun getFilmInCollection(filmId: Int, collectionId: Int): Boolean{
        return dataDao.getFilmInCollection(filmId, collectionId) != null
    }

    fun getCollectionRecord(nameCollection: String):CollectionDB? = dataDao.getCollectionRecord(nameCollection)
}