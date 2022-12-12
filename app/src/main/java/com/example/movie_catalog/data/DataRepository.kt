package com.example.movie_catalog.data

import com.example.movie_catalog.App
import com.example.movie_catalog.data.api.DataSourceAPI
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.room.DataSourceDB
import com.example.movie_catalog.entity.DataCentre
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.*
import com.example.movie_catalog.entity.plug.Plug
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private val dataSourceAPI = DataSourceAPI()

    private val dataSourceDB = DataSourceDB()

    suspend fun routerGetApi(page:Int): List<Film>{
        return when (App.kitApp){
            Kit.PREMIERES -> getPremieres()
            Kit.POPULAR -> getTop(page, Kit.POPULAR)
            Kit.TOP250 -> getTop(page, Kit.TOP250)
            Kit.SERIALS -> getSerials(page,Kit.SERIALS)
//            Kit.RANDOM1 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
//            Kit.RANDOM2 -> getFilters(page, dataRepository.takeKit()!!.countryID,
//                dataRepository.takeKit()!!.genreID)
            else -> emptyList()
        }
    }

    suspend fun getPremieres(): List<Film>{
//        dataSourceAPI.getPremieres()
        DataCentre.addFilms(Plug().filmPlug)
        return DataCentre.films.filter { it.kit == Kit.PREMIERES }
    }

    suspend fun getTop(page:Int, kit:Kit): List<Film> {
        dataSourceAPI.getTop(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }

    suspend fun getFilters(page:Int, kit:Kit): List<Film>{
        dataSourceAPI.getFilters(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }

    suspend fun getSerials(page:Int, kit:Kit): List<Film>{
        dataSourceAPI.getSerials(page, kit)
        return DataCentre.films.filter { it.kit == kit }
    }

    suspend fun getSimilar(id: Int): List<Film> {
        dataSourceAPI.getSimilar(id)
        return DataCentre.films.filter { it.similar == id }
    }

    suspend fun getGenres() = dataSourceAPI.getGenres()

    suspend fun getPersons(id: Int) = dataSourceAPI.getPersons(id)

    suspend fun getPersonInfo(id: Int) = dataSourceAPI.getPersonInfo(id)

    suspend fun getInfoFilmSeason(id: Int): InfoFilmSeasons {
        return Plug().infoFilmSeasonPlug
//        return dataSourceAPI.getInfoFilmSeason(id)
    }

    fun getGallery(): Gallery {

        val gallery = Gallery()
        if (DataCentre.currentFilmId != null){
            val film = DataCentre.films.find { it.filmId == DataCentre.currentFilmId }
            if (film != null){
                gallery.listImage = film.images
                gallery.listImage.forEach { image ->
                    if (gallery.tabs.find { it == image.tab } == null){
                        gallery.tabs.add(image.tab!!)
                    }
                }
            }
        }
        return gallery
    }

    suspend fun getImages(): List<Images>{
        return DataCentre.films.find { it.filmId == DataCentre.currentFilmId }!!.images
    }

    fun putFilm(item:Film){
        DataCentre.currentFilmId = item.filmId
    }
    fun putPersonDTO(item:PersonDTO){
        App.personDTOApp = item
    }
    fun putPerson(item: Person){
        App.personApp = item
    }
    fun putKit(item: Kit){
        App.kitApp = item
    }
    fun putGallery(item:Gallery){
        App.galleryApp = item
    }
//    fun putGalleryViewingPosition(item:Int){
//        App.galleryApp!!.viewingPosition = item
//    }
//    fun putImage(item: MutableList<FilmImageUrlDTO>){
//        App.imageApp = item
//    }
    fun putListFilm(item:List<Film>){
        App.listFilmApp = item
    }
    fun putListPersonDTO(item:List<PersonDTO> ){
        App.listPersonDTOApp = item
    }
    fun putFilmInfoSeasons(item:InfoFilmSeasons){
        App.filmInfoSeasonsApp = item
    }
    fun takeFilm() = App.filmApp
    fun takePersonDTO() = App.personDTOApp
    fun takePerson() = App.personApp
    fun takeKit() = App.kitApp
    fun takeGallery() = App.galleryApp
//    suspend fun takeImage() = App.imageApp
    fun takeListFilm() = App.listFilmApp
    fun takeListPersonDTO() = App.listPersonDTOApp
    fun takeFilmInfoSeasons() = App.filmInfoSeasonsApp

// FUNCTION DB
    fun changeViewed(id: Int) {
        dataSourceDB.setViewed(id)
    }
    fun changeFavorite(id: Int) {
        dataSourceDB.setFavorite(id)
    }
    fun changeBookmark(id: Int) {
        dataSourceDB.setBookmark(id)
    }
}