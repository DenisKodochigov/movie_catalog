package com.example.movie_catalog

import android.app.Application
import android.content.Context
import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
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
        var personApp = Person()
        var kitApp: Kit? = null
        var galleryApp: Gallery? = null
        var imageApp = mutableListOf<FilmImageUrlDTO>()
        var listFilmApp: List<Film> = emptyList()
        var listPersonDTOApp: List<PersonDTO> = emptyList()
        var filmInfoSeasonsApp = InfoFilmSeasons()
    }
}