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

    fun getFilm(film: Film): FilmDB? {
        return dataDao.getFilm(film.filmId!!)
    }

    fun getFilms(): List<FilmDB>? {
        return dataDao.getFilms()
    }
    private fun checkFilmInCollection(film: Film){
//        if ( ! dataDao.existFilmInCollections(film.filmId!!)) {
//            dataDao.deleteByIdFilmDB(film.filmId)
//        }
    }
    fun addFilm(filmDB: FilmDB){
        dataDao.insert(filmDB)
    }
    fun viewedFlow(id: Int): Flow<Boolean> = dataDao.setViewedFlow(id)
    fun bookmarkFlow(id: Int): Flow<Boolean> = dataDao.setBookmarkFlow(id)
    fun favoriteFlow(id: Int): Flow<Boolean> = dataDao.setFavoriteFlow(id)

    fun setViewed(film: Film) {
        val filmN = film.copy()
        val filmDB = dataDao.getFilm(filmN.filmId!!)
        if (filmDB == null) {
            filmN.viewed = true //Добавляем фмльм в базу, это возможно полсе того как поставили признак просмотрено
            dataDao.insert(FilmDB(idFilm = filmN.filmId, msg = "", filmN))
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

    fun getViewedFilmsId() = dataDao.getViewedFilms(true)

    fun getListFilmsIdInCollection(id: Int) = dataDao.getListFilmsInCollection(id)

    fun getListFilmDB(listId: List<Int>) = dataDao.getFilmInList(listId)

    fun clearViewedFilm(){
        dataDao.setAllViewed(false)
    }

    fun clearBookmarkFilm(){
        dataDao.deleteByIdCrossFC(2)
    }

    fun deleteCollection(nameCollection: String){
        val colDb = dataDao.getCollectionRecord(nameCollection)
        colDb?.let{
            if (it.idCollection != 1 && it.idCollection != 2) {
                dataDao.deleteByIdCrossFC(it.idCollection)
                dataDao.deleteByIdCollection(it.idCollection)
            }
        }
    }
}