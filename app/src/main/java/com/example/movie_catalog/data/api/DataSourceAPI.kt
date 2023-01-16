package com.example.movie_catalog.data.api

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.entity.enumApp.MonthKinopoisk
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.enumApp.ImageGroup
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import java.util.*
import javax.inject.Inject
/*
The class participates in data loading via Retrofit
 */
class DataSourceAPI @Inject constructor() {
// Request a list of countries and genres. Randomly selecting two pairs of country-genre
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
//Request for information about the seasons of the series
    suspend fun getInfoFilmSeason(film: Film) {
        val filmInfoSeasons = InfoFilmSeasons()
        filmInfoSeasons.infoFilm = film.filmId?.let { retrofitApi.getFilmInfo(it, DataCentre.headers) }
    //We check for information about the seasons in the movie
        if (filmInfoSeasons.infoFilm!!.serial!!) {
            //Request information about the seasons in the movie
            filmInfoSeasons.infoSeasons = film.filmId?.let { retrofitApi.getSeasons(it, DataCentre.headers) }
        }
    // Adding information about the seasons to the movie object
        DataCentre.addFilm(filmInfoSeasons, film)
    }
//Request a list of films from the premiere category.
    @SuppressLint("SimpleDateFormat")
    suspend fun getPremieres() {
        //Preparing date data for a request for premieres
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
//      Request for premieres
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
    // Adding information about the seasons to the movie object
        DataCentre.addFilms(premieres)
//        DataCentre.addFilms(Plug().filmPlug)
    }
    //Request a list of films from the TOP category.
    suspend fun getTop(page: Int, kit: Kit) {
        DataCentre.addFilms(retrofitApi.getTop(page, kit.query, DataCentre.headers), kit)
    }
//Request a list of movies by parameters
    suspend fun getFilters(page: Int, kit: Kit): List<Linker> {
        //Get filter data
        val filter = DataCentre.takeSearchFilter()
        return when (kit){
            //Get a list of movies for a random set of country genre
            Kit.RANDOM1 -> DataCentre.addFilms(retrofitApi.getFilters(page,
                SortingField.RATING.toString(), TypeFilm.FILM.toString(), 1, 10,
                1990, 2100, "", kit.countryID, kit.genreID, DataCentre.headers), kit)
            //Get a list of movies for a set of country genre
            Kit.RANDOM2 -> DataCentre.addFilms(retrofitApi.getFilters(page,
                SortingField.RATING.toString(), TypeFilm.FILM.toString(), 1, 10,
                1990, 2100, "", kit.countryID, kit.genreID, DataCentre.headers), kit)
            //Get a list of movies by the specified parameters
            Kit.SEARCH -> DataCentre.addFilms(retrofitApi.getFilters(page, filter.sorting.toString(),
                filter.typeFilm.toString(), filter.rating.first.toInt(), filter.rating.second.toInt(),
                filter.year.first, filter.year.second, filter.keyWord, filter.country.id,
                filter.genre.id, DataCentre.headers), kit)
            //Get a list of TV series
            Kit.SERIALS -> DataCentre.addFilms(retrofitApi.getFilters(page, SortingField.RATING.toString(),
                TypeFilm.SERIALS.toString(), 1, 10, 1900, 2100, DataCentre.headers), kit)
            else -> { emptyList()}
        }
    }
//Get a list of similar movies
    suspend fun getSimilar(film: Film) {
        DataCentre.addFilms(retrofitApi.getSimilar(film.filmId!!, DataCentre.headers), film)
    }
//Get a list of photos for the movie
    suspend fun getImages(film: Film) {
        //Sorting through the possible types of images from the movieм
        ImageGroup.values().forEach { tab ->
            var page = 1
            val filmImageDTO = retrofitApi.getGallery(film.filmId!!, tab.toString(), page, DataCentre.headers)
            //If the images received
            if (filmImageDTO.total!! > 0) {
                //Add DataCentre
                DataCentre.addImage(film, tab, filmImageDTO)
                while (filmImageDTO.totalPages!! >= page) {
                    //We get data from the following request pages.
                    DataCentre.addImage(film, tab,
                        retrofitApi.getGallery(film.filmId, tab.toString(), page++, DataCentre.headers)
                    )
                }
            }
        }
    }
//Request a list of people involved in the film
    suspend fun getPersons(film: Film) {
//        Log.d("KDS start retrofit", "getPersons start")
        DataCentre.addPerson(retrofitApi.getPersons(film.filmId!!, DataCentre.headers), film)
    }
//Request for additional information on a person
    suspend fun getPersonInfo(person: Person) {
        person.personId?.let { retrofitApi.getPersonInfo(it, DataCentre.headers) }?.let {
            DataCentre.addPersonInfo(it) }
//        Log.d("KDS start retrofit", "getPersonInfo start")
    }
}