package com.example.movie_catalog.data.repositary.api

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.movie_catalog.App
import com.example.movie_catalog.Constants
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.data.repositary.api.film_info.SimilarItemDTO
import com.example.movie_catalog.data.repositary.api.home.MonthKinopoisk
import com.example.movie_catalog.data.repositary.api.home.filter.FilterFilmDTO
import com.example.movie_catalog.data.repositary.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.repositary.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.data.repositary.api.home.getKit.SelectedKit
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.repositary.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.filminfo.FilmInfoSeasons
import java.util.*
import javax.inject.Inject

class DataSourceAPI @Inject constructor() {

    suspend fun routerGetApi(page:Int):List<Film>{
        return when (App.kitApp){
            Kit.PREMIERES -> getPremieres()
            Kit.POPULAR -> getTop(page, "TOP_100_POPULAR_FILMS")
            Kit.TOP250 -> getTop(page, "TOP_250_BEST_FILMS")
            Kit.SERIALS -> getSerials(page)
            Kit.RANDOM1 -> getFilters(page, App.kitApp!!.countryID, App.kitApp!!.genreID)
            Kit.RANDOM2 -> getFilters(page, App.kitApp!!.countryID, App.kitApp!!.genreID)
            else -> emptyList()
        }
    }

    suspend fun getGenres(): SelectedKit {
//        val genreList = retrofitApi.getGenres()

//        return SelectedKit( genre1 = genreList.genres!!.random(),
//            country1 = genreList.countries!!.random(),
//            genre2 = genreList.genres.random(),
//            country2 = genreList.countries.random()
        return SelectedKit(
            GenreIdDTO(id = 11, genre = "боевик"), CountryIdDTO(id = 1, country = "США"),
            GenreIdDTO(id = 4, genre = "мелодрама"), CountryIdDTO(id = 1, country = "Франция")
        )
    }

    suspend fun getActors(id: Int): List<PersonDTO> {
        return retrofitApi.getPersons(id)
    }

    suspend fun getInfoSeasson(id: Int): FilmInfoSeasons {
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

    suspend fun getTop(page:Int, type: String): List<Film> {
        return copyTopToFilm(retrofitApi.getTop(page, type).films)
    }

    suspend fun getFilters(page:Int, genre:Int, country:Int): List<Film> {
        return copyFilterToFilm(retrofitApi.getFilters(page, country,genre).items!!)
    }

    suspend fun getSerials(page:Int): List<Film> {
        return copyFilterToFilm(retrofitApi.getSerials(page).items!!)
    }

    suspend fun getGallery(id:Int, type:String): FilmImageDTO {
        return retrofitApi.getGallery(id, type)
    }

    suspend fun getSimilar(id:Int): List<Film> {
        return copySimilarToFilm(retrofitApi.getSimilar(id).items!!)
    }

    private suspend fun copySimilarToFilm(filmList: List<SimilarItemDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.forEach {
            films.add(
                Film(
                    filmId = it.filmId,
                    imdbId = null,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = null,
                    posterUrlPreview = it.posterUrlPreview,
                    countries = emptyList(),
                    genres = retrofitApi.getFilmInfo(it.filmId!!).genres!!,
                    viewed = false,
                    favorite = setFavorite(it.filmId),
                    bookmark = setBookMark(it.filmId)
                )
            )
        }
        Log.d("KDS", "size list=${films.size}")
        films.shuffle()
        return films
    }

    private fun copyToFilm(filmList: List<FilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.forEach {
            films.add(
                Film(
                    filmId = it.kinopoiskId,
                    imdbId = null,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = null,
                    posterUrlPreview = it.posterUrlPreview,
                    countries = it.countries,
                    genres = it.genres,
                    viewed = false,
                    favorite = setFavorite(it.kinopoiskId!!),
                    bookmark = setBookMark(it.kinopoiskId)
                )
            )
        }
        films.shuffle()
        return films
    }

    private fun copyTopToFilm(filmList: List<TopFilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.forEach {
            films.add(
                Film(
                    filmId = it.filmId,
                    imdbId = null,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = it.rating,
                    posterUrlPreview = it.posterUrlPreview,
                    countries = it.countries,
                    genres = it.genres,
                    viewed = false,
                    favorite = setFavorite(it.filmId!!),
                    bookmark = setBookMark(it.filmId)
                )
            )
        }
        films.shuffle()
        return films
    }

    private fun copyFilterToFilm(filmList: List<FilterFilmDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.forEach {
            films.add(
                Film(
                    filmId = it.kinopoiskId,
                    imdbId = it.imdbId,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = it.ratingKinopoisk.toString(),
                    posterUrlPreview = it.posterUrlPreview,
                    countries = it.countries,
                    genres = it.genres,
                    viewed = false,
                    favorite = setFavorite(it.kinopoiskId!!),
                    bookmark = setBookMark(it.kinopoiskId)
                )
            )
        }
        films.shuffle()
        return films
    }

    private fun setFavorite(id: Int):Boolean{
        return false
    }

    private fun setBookMark(id: Int):Boolean{
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun selectPremieresTwoWeeks(premieres: PremieresDTO): PremieresDTO {

        //Calculate date next two weeks in milliseconds
        val currentTime= Calendar.getInstance()
        currentTime.add(Calendar.WEEK_OF_YEAR, Constants.PREMIERES_WEEKS)
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