package com.example.movie_catalog.data.room

interface ActionDB {
    fun getViewed(id:Int):Boolean
    fun getFavorite(id:Int):Boolean
    fun getBookmark(id:Int):Boolean
    fun getFilm(id:Int): FilmDB
    fun updateRecord(film: FilmDB)
    fun setViewed(id:Int)
    fun setFavorite(id:Int)
    fun setBookmark(id:Int)
}