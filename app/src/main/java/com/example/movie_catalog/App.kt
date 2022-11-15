package com.example.movie_catalog

import android.app.Application
import com.example.movie_catalog.entity.Film
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        var filmApp = Film()

    }
}