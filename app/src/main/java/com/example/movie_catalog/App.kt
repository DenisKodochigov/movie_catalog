package com.example.movie_catalog

import android.app.Application
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.entity.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Images
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        var filmApp = Film()
        var personDTOApp = PersonDTO()
        var imageApp = FilmImageUrlDTO()
        var kitApp: Kit? = null
        val imagesApp: Images? = null
    }
}