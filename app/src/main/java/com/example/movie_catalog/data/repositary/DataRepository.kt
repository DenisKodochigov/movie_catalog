package com.example.movie_catalog.data.repositary

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.Constants.PREMIERES_WEEKS
import com.example.movie_catalog.data.repositary.api.retrofitApi
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.filminfo.FilmInfoSeasons
import com.example.movie_catalog.entity.filminfo.person.Person
import com.example.movie_catalog.entity.home.MonthKinopoisk
import com.example.movie_catalog.entity.home.filter.FilterFilm
import com.example.movie_catalog.entity.home.premieres.Premieres
import com.example.movie_catalog.entity.home.top.TopFilms
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

    suspend fun getActors(id: Int): List<Person> {
        return retrofitApi.getActors(id)
    }

    suspend fun getFilmInfo(id: Int): FilmInfoSeasons {
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
    suspend fun getPopular(): TopFilms {
        val page = 1
        return retrofitApi.getTop(page,"")
    }
    suspend fun getTop250(): TopFilms {
        val page = 1
        return retrofitApi.getTop(page,"")
    }
    suspend fun getFilters(country:Int, genre:Int): FilterFilm {
        val page = 1
        return retrofitApi.getFilters(country,genre,page)
    }
    suspend fun getSerials(): FilterFilm {
        val page = 1
        return retrofitApi.getSerials(page)
    }
    @SuppressLint("SimpleDateFormat")
    fun selectPremieresTwoWeeks(premieres: Premieres): Premieres {

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