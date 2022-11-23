package com.example.movie_catalog.data.repositary

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.Constants.PREMIERES_WEEKS
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.data.repositary.api.home.MonthKinopoisk
import com.example.movie_catalog.data.repositary.api.home.filter.FilterFilmDTO
import com.example.movie_catalog.data.repositary.api.home.getKit.SelectedKit
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.repositary.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import com.example.movie_catalog.data.repositary.api.retrofitApi
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.filminfo.FilmInfoSeasons
import com.example.movie_catalog.entity.home.Film
import java.util.*
import javax.inject.Inject

class DataRepository @Inject constructor() {

    suspend fun getGenres(): SelectedKit {
        val genreList = retrofitApi.getGenres()

        return SelectedKit( genre1 = genreList.genres!!.random(),
                            country1 = genreList.countries!!.random(),
                            genre2 = genreList.genres.random(),
                            country2 = genreList.countries.random())
    }

    suspend fun getActors(id: Int): List<PersonDTO> {
        return retrofitApi.getActors(id)
    }

    suspend fun getFilmInfo(id: Int): FilmInfoSeasons {
        val filmInfoSeasons = FilmInfoSeasons()
        filmInfoSeasons.filmInfoDTO = retrofitApi.getFilmInfo(id)
//        Log.d("KDS1", "filmInfoSeasons = ${filmInfoSeasons.toString()}")
        if (filmInfoSeasons.filmInfoDTO!!.serial!!) {
            filmInfoSeasons.seasonsDTO = retrofitApi.getSeasons(id)
        }
        return filmInfoSeasons
    }

    suspend fun getPremieres(): List<Film> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth)
//        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        return copyToFilm(selectPremieresTwoWeeks(premieres).items)
    }

    suspend fun getTop(type: String): List<Film> {
        val topFilms = retrofitApi.getTop(1,type)
        val countPage = topFilms.pagesCount!!.toInt()
        if (countPage > 1) {
            for (i in 2 .. countPage){
                val _topFilm = retrofitApi.getTop(1,type)
                _topFilm.films.addAll(topFilms.films)
            }
        }
//        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        return copyTopToFilm(topFilms.films)
    }

    suspend fun getFilters(genre:Int, country:Int): List<Film> {
        return copyFilterToFilm(retrofitApi.getFilters(country,genre).items!!)
    }

    suspend fun getSerials(): List<Film> {
        return copyFilterToFilm(retrofitApi.getSerials().items!!)
    }

    private fun copyToFilm(filmList: List<FilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.shuffled()
        filmList.forEach {
            films.add(
                Film(kinopoiskId = it.kinopoiskId,
                filmId = null,
                imdbId = null,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                rating = null,
                posterUrlPreview = it.posterUrlPreview,
                countries = it.countries,
                genres = it.genres
            )
            )
        }
        return films
    }

    private fun copyTopToFilm(filmList: List<TopFilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.shuffled()
        filmList.forEach {
            films.add(
                Film(kinopoiskId = null,
                filmId = it.filmId,
                imdbId = null,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                rating = it.rating,
                posterUrlPreview = it.posterUrlPreview,
                countries = it.countries,
                genres = it.genres
            )
            )
        }
        return films
    }

    private fun copyFilterToFilm(filmList: List<FilterFilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.shuffled()
        filmList.forEach {
            films.add(
                Film(kinopoiskId = it.kinopoiskId,
                filmId = null,
                imdbId = it.imdbId,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                rating = it.ratingKinopoisk,
                posterUrlPreview = it.posterUrlPreview,
                countries = it.countries,
                genres = it.genres
            )
            )
        }
        return films
    }

    @SuppressLint("SimpleDateFormat")
    fun selectPremieresTwoWeeks(premieres: PremieresDTO): PremieresDTO {

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