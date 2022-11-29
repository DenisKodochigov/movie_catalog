package com.example.movie_catalog

import android.app.Application
import android.content.Context
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Gallery
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }
    companion object{
        lateinit var context: Context
        var filmApp = Film()
        var personDTOApp = PersonDTO()
        var kitApp: Kit? = null
        var galleryApp: Gallery? = null
        var imageApp = mutableListOf<FilmImageUrlDTO>()
    }
}