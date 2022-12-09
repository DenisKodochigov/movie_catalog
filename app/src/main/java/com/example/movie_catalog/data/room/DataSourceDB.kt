package com.example.movie_catalog.data.room

import javax.inject.Inject

class DataSourceDB  @Inject constructor(private val dataDao: DataDao){

    fun getViewed(id:Int):Boolean{
        return dataDao.getViewed(id)
    }
    fun getFavorite(id:Int):Boolean{
        return dataDao.getFavorite(id)
    }
    fun getBookmark(id:Int):Boolean{
        return dataDao.getBookmark(id)
    }
    fun getFilm(id:Int): FilmDB {
        return dataDao.getFilm(id)
    }
    fun updateRecord(film: FilmDB){
        dataDao.update(film)
    }

}