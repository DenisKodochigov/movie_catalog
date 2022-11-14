package com.example.movie_catalog.data.repositary

import android.util.Log
import com.example.movie_catalog.data.repositary.api.retrofitApi
import com.example.movie_catalog.entity.MonthKinopoisk
import com.example.movie_catalog.entity.Premieres
import java.util.*
import javax.inject.Inject

class DataRepository @Inject constructor() {

    suspend fun getGenres(): Map<String, String>{
        val genreList = retrofitApi.getGenres()
        val genre1 = genreList.genres?.random()?.genre.toString().replaceFirstChar { it.uppercase() } + " " + genreList.countries?.random()?.country.toString()
        val genre2 = genreList.genres?.random()?.genre.toString().replaceFirstChar { it.uppercase() } + " " + genreList.countries?.random()?.country.toString()
        Log.d("KDS","genre1=$genre1; genre2=$genre2")
        return mapOf("genre1" to genre1, "genre2" to genre2)
    }

    suspend fun getPremieres(): Premieres {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
        var premieres = retrofitApi.getPremieres(currentYear, currentMonth)
        
        if (premieres.total!! > 20){
            premieres.items.take(20)
            premieres.items[20].genres[0].genre = ""
            premieres.items[20].nameRu=""
            premieres.items[20].posterUrlPreview = "@drawable/ic_baseline_arrow_circle_right_24"
        }
        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        return premieres
    }
}