package com.example.movie_catalog.data.api

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.data.api.home.MonthKinopoisk
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.enumApp.ImageGroup
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import java.util.*
import javax.inject.Inject

class DataSourceAPI @Inject constructor() {

    suspend fun getRandomKitName(): SelectedKit {
        val genreList = retrofitApi.getGenres(DataCentre.headers)
        DataCentre.addCountryGenres(genreList)
        return SelectedKit(
            genre1 = genreList.genres!!.random(),
            country1 = genreList.countries!!.random(),
            genre2 = genreList.genres.random(),
            country2 = genreList.countries.random()
        )
//        return SelectedKit(
//            GenreIdDTO(id = 11, genre = "боевик"), CountryIdDTO(id = 1, country = "США"),
//            GenreIdDTO(id = 4, genre = "мелодрама"), CountryIdDTO(id = 1, country = "Франция")
//        )
    }

    suspend fun getInfoFilmSeason(film: Film) {
        val filmInfoSeasons = InfoFilmSeasons()
        filmInfoSeasons.infoFilm = film.filmId?.let { retrofitApi.getFilmInfo(it, DataCentre.headers) }
        //        Log.d("KDS start retrofit", "getFilmInfo start")
        if (filmInfoSeasons.infoFilm!!.serial!!) {
            filmInfoSeasons.infoSeasons = film.filmId?.let { retrofitApi.getSeasons(it, DataCentre.headers) }
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
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth, DataCentre.headers)
        //Calculate date next two weeks in milliseconds
        val currentTime = Calendar.getInstance()
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

    suspend fun getTop(page: Int, kit: Kit) {
        DataCentre.addFilms(retrofitApi.getTop(page, kit.query, DataCentre.headers), kit)
    }

    //        order=RATING&type=FILM&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000"
    suspend fun getFilters(page: Int, kit: Kit): List<Linker> {

        val filter = DataCentre.takeSearchFilter()
        return when (kit){
            Kit.RANDOM1 -> DataCentre.addFilms(retrofitApi.getFilters(page,
                SortingField.RATING.toString(), TypeFilm.FILM.toString(), 1, 10,
                1990, 2100, "", kit.countryID, kit.genreID, DataCentre.headers), kit)
            Kit.RANDOM2 -> DataCentre.addFilms(retrofitApi.getFilters(page,
                SortingField.RATING.toString(), TypeFilm.FILM.toString(), 1, 10,
                1990, 2100, "", kit.countryID, kit.genreID, DataCentre.headers), kit)
            Kit.SEARCH -> DataCentre.addFilms(retrofitApi.getFilters(page, filter.sorting.toString(),
                filter.typeFilm.toString(), filter.rating.first.toInt(), filter.rating.second.toInt(),
                filter.year.first, filter.year.second, filter.keyWord, filter.country.id,
                filter.genre.id, DataCentre.headers), kit)
            Kit.SERIALS -> DataCentre.addFilms(retrofitApi.getFilters(page, SortingField.RATING.toString(),
                TypeFilm.SERIALS.toString(), 1, 10, 1900, 2100, DataCentre.headers), kit)
            else -> { emptyList()}
        }
    }

    suspend fun getSimilar(film: Film) {
        DataCentre.addFilms(retrofitApi.getSimilar(film.filmId!!, DataCentre.headers), film)
    }

    suspend fun getImages(film: Film) {
//        Log.d("KDS start retrofit", "getGallery start")
        ImageGroup.values().forEach { tab ->
            var page = 1
            val filmImageDTO = retrofitApi.getGallery(film.filmId!!, tab.toString(), page, DataCentre.headers)
            if (filmImageDTO.total!! > 0) {
                DataCentre.addImage(film, tab, filmImageDTO)
                while (filmImageDTO.totalPages!! >= page) {
                    DataCentre.addImage(film, tab,
                        retrofitApi.getGallery(film.filmId, tab.toString(), page++, DataCentre.headers)
                    )
                }
            }
        }
    }

    suspend fun getPersons(film: Film) {
//        Log.d("KDS start retrofit", "getPersons start")
        DataCentre.addPerson(retrofitApi.getPersons(film.filmId!!, DataCentre.headers), film)
    }

    suspend fun getPersonInfo(person: Person) {
        person.personId?.let { retrofitApi.getPersonInfo(it, DataCentre.headers) }?.let {
            DataCentre.addPersonInfo(it) }
//        Log.d("KDS start retrofit", "getPersonInfo start")
    }
}