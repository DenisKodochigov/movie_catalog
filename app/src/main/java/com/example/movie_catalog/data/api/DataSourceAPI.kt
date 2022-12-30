package com.example.movie_catalog.data.api

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.data.api.home.MonthKinopoisk
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.DataCentre
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ImageGroup
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import java.util.*
import javax.inject.Inject

class DataSourceAPI @Inject constructor() {

    suspend fun getRandomKitName(): SelectedKit {
        val genreList = retrofitApi.getGenres()
        DataCentre.addCountryGenres(genreList)
        return SelectedKit( genre1 = genreList.genres!!.random(),
            country1 = genreList.countries!!.random(),
            genre2 = genreList.genres.random(),
            country2 = genreList.countries.random())

//        return SelectedKit(
//            GenreIdDTO(id = 11, genre = "боевик"), CountryIdDTO(id = 1, country = "США"),
//            GenreIdDTO(id = 4, genre = "мелодрама"), CountryIdDTO(id = 1, country = "Франция")
//        )
    }

    suspend fun getInfoFilmSeason(film: Film) {
        val filmInfoSeasons = InfoFilmSeasons()
            filmInfoSeasons.infoFilm = film.filmId?.let { retrofitApi.getFilmInfo(it) }
    //        Log.d("KDS start retrofit", "getFilmInfo start")
            if (filmInfoSeasons.infoFilm!!.serial!!) {
                filmInfoSeasons.infoSeasons = film.filmId?.let { retrofitApi.getSeasons(it) }
    //            Log.d("KDS start retrofit", "getSeasons start id=$id")
            }
            DataCentre.addFilm(filmInfoSeasons, film)
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun getPremieres() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
//        Log.d("KDS start retrofit", "getPremieres start")
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth)
        //Calculate date next two weeks in milliseconds
        val currentTime= Calendar.getInstance()
        currentTime.add(Calendar.WEEK_OF_YEAR, Constants.PREMIERES_WEEKS)
        val twoWeeksNext = currentTime.timeInMillis

        //Edit list films. We leave the films that will premiere in the next two weeks.
        val itemsIterator = premieres.items.iterator()
        while (itemsIterator.hasNext()) {
            val item = itemsIterator.next()
            val premierTime = SimpleDateFormat("yyyy-MM-dd").parse(item.premiereRu).time
            if (premierTime >= twoWeeksNext) {
                itemsIterator.remove()
            }
        }
        premieres.items.shuffle()    //Mixing the films
        DataCentre.addFilms(premieres)
//        DataCentre.addFilms(Plug().filmPlug)
    }

    suspend fun getTop(page:Int, kit: Kit) {
        DataCentre.addFilms(retrofitApi.getTop(page, kit.query), kit)
    }

    //        order=RATING&type=FILM&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000"
    suspend fun getFilters(page:Int, kit: Kit, countryID: Int?, genreID: Int?,
        sorting: String = SortingField.RATING.toString(), type: String = TypeFilm.FILM.toString(),
        ratingFrom: Int = 0, ratingTo: Int = 10, yearFrom: Int = 1900, yearTo: Int = 2023) {
//        Log.d("KDS start retrofit", "getFilters start")
//        DataCentre.addFilms(retrofitApi.getFilters(page, sorting, type, ratingFrom, ratingTo,
//                         yearFrom, yearTo, countryID, genreID), kit)
        val result = retrofitApi.getFilters(page, sorting, type, ratingFrom, ratingTo,
            yearFrom, yearTo, countryID, genreID)
        DataCentre.addFilms(result, kit)
    }

    suspend fun getSerials(page:Int, kit: Kit){
//        Log.d("KDS start retrofit", "getSerials start")
        DataCentre.addFilms(retrofitApi.getSerials(page), kit)
    }

    suspend fun getSimilar(film: Film) {
        DataCentre.addFilms(retrofitApi.getSimilar(film.filmId!!), film)
    }

    suspend fun getImages(film: Film){
//        Log.d("KDS start retrofit", "getGallery start")
        ImageGroup.values().forEach { tab ->
            var page = 1
            val filmImageDTO = retrofitApi.getGallery(film.filmId!!,tab.toString(), page)
            if ( filmImageDTO.total!! > 0){
                DataCentre.addImage(film, tab, filmImageDTO)
                while (filmImageDTO.totalPages!! >= page){
                    DataCentre.addImage(film, tab, retrofitApi.getGallery(film.filmId,tab.toString(), page ++))
                }
            }
        }
    }

    suspend fun getPersons(film: Film){
//        Log.d("KDS start retrofit", "getPersons start")
        DataCentre.addPerson( retrofitApi.getPersons(film.filmId!!), film)
    }

    suspend fun getPersonInfo(person: Person) {
        person.personId?.let { retrofitApi.getPersonInfo(it) }?.let { DataCentre.addPersonInfo(it) }
//        Log.d("KDS start retrofit", "getPersonInfo start")
    }
}