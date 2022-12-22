package com.example.movie_catalog.data

import android.util.Log
import com.example.movie_catalog.data.api.DataSourceAPI
import com.example.movie_catalog.data.room.CollectionFilmDB
import com.example.movie_catalog.data.room.DataSourceDB
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ProfKey
import com.example.movie_catalog.entity.filminfo.ImageFilm
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private val dataSourceAPI = DataSourceAPI()
    private val dataSourceDB = DataSourceDB()

    // home  fragment
    suspend fun getGenres() = dataSourceAPI.getGenres()

    suspend fun getPremieres(): List<Linker> {
        var linkers = DataCentre.linkers.filter { it.kit == Kit.PREMIERES }
        if (linkers.isEmpty()) {
            dataSourceAPI.getPremieres()
            linkers = DataCentre.linkers.filter { it.kit == Kit.PREMIERES }
        }
        Log.d("KDS", "${linkers[0].kit}")
//        linkers = Plug.listLinkers
        return linkers
    }

    suspend fun getTop(page: Int, kit: Kit): List<Linker> {
        var listBinder = DataCentre.linkers.filter { it.kit == kit }
        if (listBinder.isEmpty()) {
            dataSourceAPI.getTop(page, kit)
            listBinder = DataCentre.linkers.filter { it.kit == kit }
        }
        return listBinder
    }

    suspend fun getSerials(page: Int, kit: Kit): List<Linker> {
        var listBinder = DataCentre.linkers.filter { it.kit == kit }
        if (listBinder.isEmpty()) {
            dataSourceAPI.getSerials(page, kit)
            listBinder = DataCentre.linkers.filter { it.kit == kit }
        }
        return listBinder
    }

    suspend fun getFilters(page: Int, kit: Kit): List<Linker> {
        var listBinder = DataCentre.linkers.filter { it.kit == kit }
        if (listBinder.isEmpty()) {
            dataSourceAPI.getFilters(page, kit)
            listBinder = DataCentre.linkers.filter { it.kit == kit }
        }
        return listBinder
    }

    // film info fragment
    suspend fun getSimilar(film: Film): List<Linker> {
        var listFilm = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        if (listFilm.isEmpty()) {
            dataSourceAPI.getSimilar(film)
            listFilm = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        }
        return listFilm
    }

    suspend fun getInfoFilmSeason(film: Film): Film? {                 //InfoFilmSeasons
        if (film.posterUrl == null) dataSourceAPI.getInfoFilmSeason(film)
        return DataCentre.films.find { it.filmId == film.filmId }
    }

    suspend fun getPersons(film: Film, job: String? = null): List<Linker> {
        if (DataCentre.linkers.count { it.person != null && it.film == film } <= 1) {
            dataSourceAPI.getPersons(film)
        }
        val listPerson = if (job == null) {
            DataCentre.linkers.filter { it.person != null && it.film == film }
        } else {
            if (job == ProfKey.ACTOR.name) {
                DataCentre.linkers.filter {
                    it.person != null && it.film == film &&
                            it.profKey?.name == ProfKey.ACTOR.name
                }
            } else {
                DataCentre.linkers.filter {
                    it.person != null && it.film == film &&
                            it.profKey?.name != ProfKey.ACTOR.name
                }
            }
        }
        return listPerson
    }

    suspend fun getImages(film: Film): List<ImageFilm> {
        if (film.images.isEmpty()) {
            dataSourceAPI.getImages(film)
        }
        return DataCentre.films.find { it == film }!!.images
    }

    //Person fragment
    suspend fun getPersonInfo(person: Person): List<Linker> {
        if (DataCentre.linkers.count { it.person == person } <= 1) {
            dataSourceAPI.getPersonInfo(person)
        }
        val listLink = DataCentre.linkers.filter { it.person == person }
        listLink.forEach { link ->
            link.film?.let { film ->
                if (film.posterUrlPreview == null) dataSourceAPI.getInfoFilmSeason(film)
            }
        }
        return DataCentre.linkers.filter { it.person == person }
    }

    fun getFilmographyData(person: Person): FilmographyData {
        val linkers = DataCentre.linkers.filter { it.person == person && it.film != null }
        val tabsKey = linkers.groupingBy { it.profKey }.eachCount().toList()
        return FilmographyData(linkers, tabsKey)
    }

    //Kit fragment
    suspend fun routerGetApi(page: Int, kit: Kit): List<Linker> {
        return when (kit) {
            Kit.POPULAR -> getTop(page, Kit.POPULAR)
            Kit.TOP250 -> getTop(page, Kit.TOP250)
            Kit.SERIALS -> getSerials(page, Kit.SERIALS)
            Kit.RANDOM1 -> getFilters(page, Kit.RANDOM1)
            Kit.RANDOM2 -> getFilters(page, Kit.RANDOM2)
            else -> emptyList()
        }
    }

    //List film fragment
    fun getFilmsForFilter(film: Film?, person: Person?): List<Linker> {
        var linkers = listOf<Linker>()
        if (film != null && person == null) {
            linkers = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        } else if (film == null && person != null) {
            linkers = DataCentre.linkers.filter { it.person == person && it.film != null }
        } else if (film != null && person != null) {
            linkers = DataCentre.linkers.filter { it.person == person && it.film == film }
        }
        return linkers
    }  // film info fragment

    //List person fragment
    fun getGallery(film: Film): Gallery {
        return Gallery(
            images = film.images,
            tabs = film.images.groupingBy { it.imageGroup }.eachCount().toList()
        )
    }

    fun takeFilm() = DataCentre.takeFilm()

    fun putFilm(film: Film) {
        DataCentre.putFilm(film)
    }

    fun putPerson(person: Person) {
        DataCentre.putPerson(person)
    }                                   // film info, list person fragment

    fun takePerson() = DataCentre.takePerson()

    fun takeKit() = DataCentre.takeKit()

    fun putKit(item: Kit) {
        DataCentre.putKit(item)
    }

    fun takeJobPerson() = DataCentre.takeJobPerson()

    fun putJobPerson(item: String) {
        DataCentre.putJobPerson(item)
    }

    // FUNCTION DB #################################################
    fun changeViewed(film: Film) {
        dataSourceDB.setViewed(film.filmId!!)
        film.viewed = !film.viewed
    }

    fun changeFavorite(film: Film) {
        dataSourceDB.setFavorite(film.filmId!!)
        film.favorite = !film.favorite
    }

    fun changeBookmark(film: Film) {
        dataSourceDB.setBookmark(film.filmId!!)
        film.bookmark = !film.bookmark
    }
    fun getCollections(filmId:Int): List<CollectionFilmDB>{
        val listCollectionFilmDB = dataSourceDB.getCollections()
        if (listCollectionFilmDB.isNotEmpty()) {
            listCollectionFilmDB.forEach {
                it.count = dataSourceDB.getCountFilmCollection(it.name).size
                it.included = dataSourceDB.getFilmInCollection(it.name, filmId)?.included == 1
                Log.d("KDS", " count = ${it.count}")
            }
        }
        return listCollectionFilmDB
    }

    fun newCollection(nameCollection: String, filmId:Int): List<CollectionFilmDB>{
        dataSourceDB.newCollection(CollectionFilmDB(name = nameCollection))
        return getCollections(filmId)
    }

    fun addRemoveFilmToCollection(nameCollection: String, filmId: Int): List<CollectionFilmDB>{
        dataSourceDB.addRemoveFilmToCollection(nameCollection, filmId)
        return getCollections(filmId)
    }
}
//################################################################################################################
