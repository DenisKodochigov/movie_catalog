package com.example.movie_catalog.data.room

import com.example.movie_catalog.App
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DataSourceDB  @Inject constructor(){

    private val dataDao = ProviderDao().getDataDao(App.context)

    fun getViewed(id:Int):Boolean{
        return dataDao.getViewed(id)
    }
    fun getFavorite(id:Int):Boolean{
        return dataDao.getFavorite(id)
    }
    fun getBookmark(id:Int):Boolean{
        return dataDao.getBookmark(id)
    }
    fun getFilm(id:Int): FilmDB? {
        return dataDao.getFilm(id)
    }
    fun updateRecord(film: FilmDB){
        dataDao.update(film)
    }

    fun setViewed(id: Int) {
        val filmDB = dataDao.getFilm(id)
        if (filmDB == null) {
            dataDao.insert(FilmDB(filmId = id, msg = "", timestamp = 0,
                id = 0, viewed = true, bookmark = false, favorite = false))
        } else {
            filmDB.viewed = !filmDB.viewed
            dataDao.update(filmDB)
        }
    }

    fun setFavorite(id: Int) {
        val filmDB = dataDao.getFilm(id)
        if (filmDB == null) {
            dataDao.insert(FilmDB(filmId = id, msg = "", timestamp = 0,
                id = 0, viewed = false, bookmark = false, favorite = true))
        } else {
            filmDB.favorite = !filmDB.favorite
            dataDao.update(filmDB)
        }
    }

    fun setBookmark(id: Int) {
        val filmDB = dataDao.getFilm(id)
        if (filmDB == null) {
            dataDao.insert(FilmDB(filmId = id, msg = "", timestamp = 0,
                id = 0, viewed = false, bookmark = true, favorite = true))
        } else {
            filmDB.bookmark = !filmDB.bookmark
            dataDao.update(filmDB)
        }
    }

    fun getCollections():List<CollectionFilmDB>{
        return dataDao.getCollection()
    }

    fun newCollection(collection: CollectionFilmDB){
        dataDao.insert(collection)
    }

    fun getCountFilmCollection(collectionId: String): List<CrossFileCollection>{
        return dataDao.getCountFilmCollection(collectionId,1)
    }

    fun addRemoveFilmToCollection(collectionId: String, filmId: Int){
        val crossFilmCollection = dataDao.getFilmInCollection(collectionId, filmId)
        if (crossFilmCollection != null){
            if (crossFilmCollection.included == 0) crossFilmCollection.included = 1
            else crossFilmCollection.included = 0
            dataDao.update(crossFilmCollection)
        } else {
            dataDao.insert(CrossFileCollection(idFilm = filmId, idCollection = collectionId, 1))
        }
    }

    fun getFilmInCollection(collectionId: String, filmId: Int): CrossFileCollection?{
        return dataDao.getFilmInCollection(collectionId,filmId)
    }

}