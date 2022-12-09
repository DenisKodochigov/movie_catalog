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
import com.example.movie_catalog.data.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.FilmographyTab
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
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

    suspend fun getPremieres(): List<Film> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = MonthKinopoisk.values()[calendar.get(Calendar.MONTH)].toString()
//        Log.d("KDS start retrofit", "getPremieres start")
        val premieres = retrofitApi.getPremieres(currentYear, currentMonth)
//        Log.d("KDS", "year=$currentYear, month=$currentMonth")
        return copyToFilm(selectPremieresTwoWeeks(premieres).items)
    }

    suspend fun getTop(page:Int, type: String): List<Film> {
//        Log.d("KDS start retrofit", "getTop start")
        return copyTopToFilm(retrofitApi.getTop(page, type).films)
    }

    suspend fun getFilters(page:Int, genre:Int, country:Int): List<Film> {
//        Log.d("KDS start retrofit", "getFilters start")
        return copyFilterToFilm(retrofitApi.getFilters(page, country,genre).items!!)
    }

    suspend fun getSerials(page:Int): List<Film> {
//        Log.d("KDS start retrofit", "getSerials start")
        return copyFilterToFilm(retrofitApi.getSerials(page).items!!)
    }

    suspend fun getGallery(id:Int, type:String, page: Int): FilmImageDTO {
//        Log.d("KDS start retrofit", "getGallery start")
        return retrofitApi.getGallery(id, type, page)
    }

    suspend fun getSimilar(id:Int): List<Film> {
//        Log.d("KDS start retrofit", "getSimilar start")
        return copySimilarToFilm(retrofitApi.getSimilar(id).items!!)
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

    private suspend fun copySimilarToFilm(filmList: List<SimilarItemDTO>): List<Film>{
        val films = mutableListOf<Film>()
        filmList.forEach {
//            Log.d("KDS start retrofit", "getFilmInfo start")
            films.add(
                Film(
                    filmId = it.filmId,
                    imdbId = null,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = null,
                    posterUrlPreview = it.posterUrlPreview,
                    countries = emptyList(),
                    genres = emptyList(),// retrofitApi.getFilmInfo(it.filmId!!).genres!!,
                    viewed = setViewed(it.filmId),
                    favorite = setFavorite(it.filmId),
                    bookmark = setBookMark(it.filmId)
                )
            )
        }
//        Log.d("KDS", "size list=${films.size}")
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
                    viewed = setViewed(it.kinopoiskId),
                    favorite = setFavorite(it.kinopoiskId),
                    bookmark = setBookMark(it.kinopoiskId)
                )
            )
        }
//        films.shuffle()
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
                    viewed = setViewed(it.filmId),
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

    private fun setFavorite(id: Int?):Boolean{
        return false
    }

    private fun setBookMark(id: Int?):Boolean{
        return false
    }

    private fun setViewed(id: Int?):Boolean{
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