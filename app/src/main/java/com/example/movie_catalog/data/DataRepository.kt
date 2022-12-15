package com.example.movie_catalog.data

import com.example.movie_catalog.Constants
import com.example.movie_catalog.data.api.DataSourceAPI
import com.example.movie_catalog.data.room.DataSourceDB
import com.example.movie_catalog.entity.DataCentre
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.filminfo.Images
import com.example.movie_catalog.entity.filminfo.Kit
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private val dataSourceAPI = DataSourceAPI()

    private val dataSourceDB = DataSourceDB()

    suspend fun getPremieres(): List<Film> {
        if (DataCentre.films.find { it.kit == Kit.PREMIERES } == null) {
            dataSourceAPI.getPremieres()
        }
        return DataCentre.films.filter { it.kit == Kit.PREMIERES }
    }                   // home fragment

    suspend fun getTop(page: Int, kit: Kit): List<Film> {
        dataSourceAPI.getTop(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }       // home fragment

    suspend fun getFilters(page: Int, kit: Kit): List<Film> {
        dataSourceAPI.getFilters(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }   // home fragment

    suspend fun getSerials(page: Int, kit: Kit): List<Film> {
        dataSourceAPI.getSerials(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }   // home fragment

    suspend fun getGenres() = dataSourceAPI.getGenres()         // home fragment

    suspend fun getInfoFilmSeason(id: Int): Film? {                 //InfoFilmSeasons
        val film = DataCentre.films.find { it.filmId == id }
        if (film?.posterUrl == null)
            DataCentre.addFilm(dataSourceAPI.getInfoFilmSeason(id), id)
//        return Plug().infoFilmSeasonPlug
        return DataCentre.films.find { it.filmId == id }
    }               // film info fragment

    suspend fun getPersons(idFilm: Int): List<Person> {
        var listPerson = getListPersonFromFilmID(idFilm)

        if (listPerson.isEmpty()) {
            DataCentre.addPerson(dataSourceAPI.getPersons(idFilm), idFilm)
            listPerson = getListPersonFromFilmID(idFilm)
        }
        return listPerson
    }             // film info fragment

    suspend fun getImages(id: Int): List<Images> {
        val film = DataCentre.films.find { it.filmId == id }
        if (film != null) {
            if (film.images.isEmpty()) {
                dataSourceAPI.getImages(id)
            }
        }
        return DataCentre.films.find { it.filmId == id }!!.images
    }                       // film info fragment

    suspend fun getSimilar(id: Int): List<Film> {
        var listFilm = DataCentre.films.filter { film -> film.similar.find { it == id } != null }
        if (listFilm.isEmpty()) {
            dataSourceAPI.getSimilar(id)
            listFilm = DataCentre.films.filter { film -> film.similar.find { it == id } != null }
        }
        return listFilm
    }               // film info fragment

    fun takeFilmsForListFilmFragment() = DataCentre.takeListForListFragment() // film info fragment

    fun putFilmsSimilar(id: Int) {
        DataCentre.putListForListFragment(DataCentre.films.find { it.filmId == id }!!.similar)
    }                       // list film fragment

    //Что бы использовать один код о выведению списка филмов, приходиться заранее формировать список
    // фильмов для вывода.
    fun putPersonFilmsForListFilmFragment(idPerson: Int) {
        DataCentre.putListForListFragment(getListFilmFromPersonId(idPerson))
    }

    fun putPersonId(id: Int) {
        DataCentre.putPersonId(id)
    }                                   // film info, list person fragment

    fun takePersonId() = DataCentre.takePersonId()

    suspend fun routerGetApi(page: Int, kit: Kit): List<Film> {
        return when (kit) {
//            Kit.PREMIERES -> getPremieres()
            Kit.POPULAR -> getTop(page, Kit.POPULAR)
            Kit.TOP250 -> getTop(page, Kit.TOP250)
            Kit.SERIALS -> getSerials(page, Kit.SERIALS)
//            Kit.RANDOM1 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
//            Kit.RANDOM2 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
            else -> emptyList()
        }
    }   //Kit fragment

    fun getGallery(id: Int): Gallery {

        val gallery = Gallery()
        val film = DataCentre.films.find { it.filmId == id }
        if (film != null) {
                gallery.listImage = film.images
                gallery.listImage.forEach { image ->
                    if (gallery.tabs.find { it == image.tab } == null) {
                        gallery.tabs.add(image.tab!!)
                    }
                }
            }

        return gallery
    }                           //Gallery fragment

    fun getListPerson(idFilm: Int, job: String) = getListPersonFromFilmID(idFilm, job)

    suspend fun getPersonInfo(id: Int): Person {
        var person = DataCentre.persons.find { it.personId == id }
        var existData = false
        person?.let { itPerson ->
            itPerson.tabs.forEach { itPersonTab ->
                if (itPersonTab.filmsID.isNotEmpty()) {
                    existData = true
                }
            }
        }
        if (!existData) dataSourceAPI.getPersonInfo(id)
        person = DataCentre.persons.find { it.personId == id }
        return fillListFilm(person!!)
    }

    fun takePersonFilmography(id: Int): Person {
        val person = DataCentre.persons.find { it.personId == id }!!
        return fillListFilm(person)
    }              // filmography fragment

    //-----------------------------------------------------------
    fun takeFilmId() = DataCentre.takeFilmId()
    fun putFilmId(item: Int) {
        DataCentre.putFilmId(item)
    }

    fun takeKit() = DataCentre.takeKit()
    fun putKit(item: Kit) {
        DataCentre.putKit(item)
    }

    fun takeJobPerson() = DataCentre.takeJobPerson()
    fun putJobPerson(item: String) {
        DataCentre.putJobPerson(item)
    }

    //-----------------------------------------------------------
// FUNCTION DB #################################################
    fun changeViewed(id: Int) {
        dataSourceDB.setViewed(id)
    }

    fun changeFavorite(id: Int) {
        dataSourceDB.setFavorite(id)
    }

    fun changeBookmark(id: Int) {
        dataSourceDB.setBookmark(id)
    }


//###############################################################

    private fun fillListFilm(person: Person): Person {
        person.tabs.forEach { itTab ->
            itTab.filmsID.forEach { itTabFilmId ->
                person.films.add(DataCentre.films.find { it.filmId == itTabFilmId }!!)
                person.films.find { it.filmId == itTabFilmId }?.professionKey = itTab.key
            }
        }
        person.films.sortBy { it.rating }
        return person
    }

    private fun getListFilmFromPersonId(personId:Int): List<Int>{
        val listFilm = mutableListOf<Int>()
        val person = DataCentre.persons.find { it.personId == personId }
        person?.let { item->
            item.tabs.forEach { itTab ->
                itTab.filmsID.forEach { itTabFilmId ->
                    val film = DataCentre.films.find { it.filmId == itTabFilmId }
                    film?.let { listFilm.add( it.filmId!! ) }
                }
            }
        }
        return listFilm
    }

    private fun getListPersonFromFilmID(filmId: Int): List<Person> {
        val listPerson = mutableListOf<Person>()
        DataCentre.persons.forEach forEach1@{ person ->
            person.tabs.forEach { tab ->
                if (tab.filmsID.find { it == filmId } != null) {
                    listPerson.add(person)
                    return@forEach1
                }
            }
        }
        return listPerson
    }

    private fun getListPersonFromFilmID(filmId: Int, job: String): List<Person> {
        val listPerson = mutableListOf<Person>()
        DataCentre.persons.forEach forEach1@{ person ->
            person.tabs.forEach { tab ->
                if (job == Constants.ACTOR) {
                    if (tab.key == Constants.ACTOR) {
                        if (tab.filmsID.find { it == filmId } != null) {
                            listPerson.add(person)
                            return@forEach1
                        }
                    }
                } else {
                    if (tab.key != Constants.ACTOR) {
                        if (tab.filmsID.find { it == filmId } != null) {
                            listPerson.add(person)
                            return@forEach1
                        }
                    }
                }
            }
        }
        return listPerson
    }

}