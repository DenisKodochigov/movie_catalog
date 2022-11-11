package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.data.repositary.api.KinopoiskAPI.Companion.retrofitApi
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmList
import com.example.movie_catalog.entity.MonthKinopoisk
import java.util.*
import javax.inject.Inject

class DataRepository @Inject constructor() {

    suspend fun getGenres(): Map<String, String>{
        val genreList = retrofitApi.getGenres()

        return mapOf("genre1" to genreList.genres?.random().toString() + " " + genreList.countries?.random().toString(),
            "genre2" to genreList.genres?.random().toString() + " " + genreList.countries?.random().toString())
    }

    suspend fun getPremieres(): List<Film>{
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()

        return retrofitApi.getPremieres(currentYear, currentMonth)
    }

}