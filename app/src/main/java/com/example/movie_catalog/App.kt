package com.example.movie_catalog

import android.app.Application
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.entity.home.Film
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        var filmApp = Film()
        var personDTOApp = PersonDTO()

    }
}