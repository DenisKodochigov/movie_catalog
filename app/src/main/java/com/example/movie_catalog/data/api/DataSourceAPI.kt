package com.example.movie_catalog.data.api

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.example.movie_catalog.App
import com.example.movie_catalog.Constants
import com.example.movie_catalog.data.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarItemDTO
import com.example.movie_catalog.data.api.home.MonthKinopoisk
import com.example.movie_catalog.data.api.home.filter.FilterFilmDTO
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.DataCentre
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmographyTab
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.entity.filminfo.Kit
import java.util.*
import javax.inject.Inject

class DataSourceAPI @Inject constructor() {

//    private val dataRepository = DataRepository()

    suspend fun routerGetApi(page:Int){
        when (App.kitApp){
            Kit.PREMIERES -> getPremieres()
            Kit.POPULAR -> getTop(page, Kit.POPULAR)
            Kit.TOP250 -> getTop(page, Kit.TOP250)
            Kit.SERIALS -> getSerials(page,Kit.SERIALS)
//            Kit.RANDOM1 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
//            Kit.RANDOM2 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
            else -> null
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

    suspend fun getInfoFilmSeason(id: Int): InfoFilmSeasons {
        val filmInfoSeasons = InfoFilmSeasons()
        filmInfoSeasons.infoFilm = retrofitApi.getFilmInfo(id)
//        Log.d("KDS start retrofit", "getFilmInfo start")
        if (filmInfoSeasons.infoFilm!!.serial!!) {
            filmInfoSeasons.infoSeasons = retrofitApi.getSeasons(id)
//            Log.d("KDS start retrofit", "getSeasons start id=$id")
        }
        return filmInfoSeasons
    }

    suspend fun getPremieres() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
//        Log.d("KDS start retrofit", "getPremieres start")
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth)
//        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        DataCentre.addFilms(selectPremieresTwoWeeks(premieres))
    }
    @SuppressLint("SimpleDateFormat")
    fun selectPremieresTwoWeeks(premieres: PremieresDTO): PremieresDTO {

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
        //Mixing the films
        premieres.items.shuffle()
        return premieres
    }

    suspend fun getTop(page:Int, kit: Kit) {
//        Log.d("KDS start retrofit", "getTop start")
        DataCentre.addFilms(retrofitApi.getTop(page, kit.query), kit)
    }

    suspend fun getFilters(page:Int, genre:Int, country:Int, kit:Kit) {
//        Log.d("KDS start retrofit", "getFilters start")
        DataCentre.addFilms(retrofitApi.getFilters(page, country,genre), kit)
    }

    suspend fun getSerials(page:Int, kit:Kit){
//        Log.d("KDS start retrofit", "getSerials start")
        DataCentre.addFilms(retrofitApi.getSerials(page), kit)
    }

    suspend fun getSimilar(id:Int) {
//        Log.d("KDS start retrofit", "getSimilar start")
        DataCentre.addFilms(retrofitApi.getSimilar(id), id)
    }

    suspend fun getGallery(id:Int, type:String, page: Int): FilmImageDTO {
//        Log.d("KDS start retrofit", "getGallery start")
        return retrofitApi.getGallery(id, type, page)
    }

    suspend fun getPersons(id: Int): List<PersonDTO> {
//        Log.d("KDS start retrofit", "getPersons start")
        return retrofitApi.getPersons(id)
    }

    suspend fun getPersonInfo(id:Int): Person {
//        Log.d("KDS start retrofit", "getPersonInfo start")
        return copyToPerson(retrofitApi.getPersonInfo(id))
    }

    private suspend fun copyToPerson(personInfoDTO: PersonInfoDTO):Person{

        val tabs = mutableListOf<FilmographyTab>()
        val listFilm: MutableList<Film> = mutableListOf()
//Fill add information for film
        personInfoDTO.films.forEach {
            val filmInfoDTO = retrofitApi.getFilmInfo(it.filmId!!)
//            Log.d("KDS start retrofit", "getFilmInfo start")
            listFilm.add(Film(
                filmId =it.filmId,
                nameRu = it.nameRu,
                nameEn = it.nameEn,
                rating = it.rating,
                posterUrlPreview = filmInfoDTO.posterUrlPreview,
                countries = emptyList(),
                genres = filmInfoDTO.genres!!,
                imdbId = null,
                viewed = setViewed(it.filmId),
                favorite = setFavorite(it.filmId),
                bookmark = setBookMark(it.filmId),
                professionKey = it.professionKey,
                startYear = filmInfoDTO.startYear
            ))
        }
//Create list used profession key
        val listProfessionalKey = personInfoDTO.films.distinctBy { it.professionKey }
//Create tab structure
        listProfessionalKey.forEach {
            tabs.add(
                FilmographyTab(
                    key = it.professionKey,
                    nameDisplay = FilmographyTab.profKey[it.professionKey]
                )
            )
        }
        listFilm.sortByDescending { it.rating }
        return Person(
                personId = personInfoDTO.personId,
                nameRu = personInfoDTO.nameRu,
                nameEn = personInfoDTO.nameEn,
                posterUrl = personInfoDTO.posterUrl,
                age = personInfoDTO.age,
                hasAwards = personInfoDTO.hasAwards,
                profession = personInfoDTO.profession,
                films = listFilm,
                tabs = tabs
        )
    }

    private fun setFavorite(id: Int?):Boolean{
        return false
    }

    private fun setBookMark(id: Int?):Boolean{
        return false
    }

    private fun setViewed(id: Int?):Boolean{
        return false
    }

}