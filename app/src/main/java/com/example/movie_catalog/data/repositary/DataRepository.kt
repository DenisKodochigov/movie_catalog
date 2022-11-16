package com.example.movie_catalog.data.repositary

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.movie_catalog.Constants.PREMIERES_WEEKS
import com.example.movie_catalog.data.repositary.api.retrofitApi
import com.example.movie_catalog.entity.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DataRepository @Inject constructor() {

    suspend fun getGenres(): Map<String, String> {
        val genreList = retrofitApi.getGenres()
        val genre1 = genreList.genres?.random()?.genre.toString()
            .replaceFirstChar { it.uppercase() } + " " + genreList.countries?.random()?.country.toString()
        val genre2 = genreList.genres?.random()?.genre.toString()
            .replaceFirstChar { it.uppercase() } + " " + genreList.countries?.random()?.country.toString()
//        Log.d("KDS", "genre1=$genre1; genre2=$genre2")
        return mapOf("genre1" to genre1, "genre2" to genre2)
    }

    suspend fun getFilmInfo(id: Int):FilmInfoSeasons{
        val filmInfoSeasons = FilmInfoSeasons()
        filmInfoSeasons.filmInfo = retrofitApi.getFilmInfo(id)
//        Log.d("KDS1", "filmInfoSeasons = ${filmInfoSeasons.toString()}")
        if (filmInfoSeasons.filmInfo!!.serial!!) {
            filmInfoSeasons.seasons = retrofitApi.getSeasons(id)
        }
        return filmInfoSeasons
    }

    suspend fun getPremieres(): Premieres {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth)
//        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        return selectPremieresTwoWeeks(premieres)
    }

    @SuppressLint("SimpleDateFormat")
    fun selectPremieresTwoWeeks(premieres: Premieres): Premieres{

        //Calculate date next two weeks in milliseconds
        val currentTime=Calendar.getInstance()
        currentTime.add(Calendar.WEEK_OF_YEAR, PREMIERES_WEEKS)
        val twoWeeksNext = currentTime.timeInMillis

//        Log.d("KDS","size=${premieres.items.size}, premieres to date=${currentTime.time}")
        //Edit list films. We leave the films that will premiere in the next two weeks.
        val itemsIterator = premieres.items.iterator()
        while (itemsIterator.hasNext()) {
            val item = itemsIterator.next()
            val premierTime = SimpleDateFormat("yyyy-MM-dd").parse(item.premiereRu).time
//            Log.d("KDS","datePremier=${item.premiereRu}, ${item.nameRu}")
            if (premierTime >= twoWeeksNext) {
//                Log.d("KDS","Deleted ${item.nameRu}")
                itemsIterator.remove()
            }
        }
        //Mixing the films
        premieres.items.shuffle()
        return premieres
    }
}