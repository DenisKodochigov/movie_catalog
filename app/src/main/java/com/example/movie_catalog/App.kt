package com.example.movie_catalog

import android.app.Application
import com.example.movie_catalog.entity.filminfo.person.Person
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        var filmDTOApp = FilmDTO()
        var filmAppTop = TopFilmDTO()
        var personApp = Person()

    }
}