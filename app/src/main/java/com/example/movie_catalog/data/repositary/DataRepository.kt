package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.data.repositary.api.KinopoiskAPI.Companion.retrofitApi
import javax.inject.Inject

class DataRepository @Inject constructor() {

    suspend fun getGenres(): Map<String, String>{
        val genreList = retrofitApi.getGenres()

        return mapOf("genre1" to genreList.genres?.random().toString() + " " + genreList.countries?.random().toString(),
            "genre2" to genreList.genres?.random().toString() + " " + genreList.countries?.random().toString())
    }
}