package com.example.movie_catalog.data

import android.util.Log
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.DataSourceAPI
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.DataSourceDB
import com.example.movie_catalog.data.room.tables.FilmDB
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ProfKey
import com.example.movie_catalog.entity.filminfo.ImageFilm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private val dataSourceAPI = DataSourceAPI()

    private val dataSourceDB = DataSourceDB()

    // home  fragment
    suspend fun getRandomKitName() = dataSourceAPI.getRandomKitName()

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

    // film info fragment
    suspend fun getSimilar(film: Film): List<Linker> {
        var listFilm = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        if (listFilm.isEmpty()) {
            dataSourceAPI.getSimilar(film)
            listFilm = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        }
        return listFilm
    }

    suspend fun getInfoFilmSeason(film: Film): Film? {
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
            Kit.SERIALS -> getFilters(page, Kit.SERIALS)
            Kit.RANDOM1 -> getFilters(page, Kit.RANDOM1)
            Kit.RANDOM2 -> getFilters(page, Kit.RANDOM2)
            Kit.SEARCH -> getFilters(page, Kit.SEARCH)
            else -> emptyList()
        }
    }

    //List film fragment
    fun getForListFilmFragment(film: Film?, person: Person?): List<Linker> {
        var linkers = listOf<Linker>()
        if (film != null && person == null) {
            linkers = DataCentre.linkers.filter { it.film == film && it.similarFilm != null }
        } else if (film == null && person != null) {
            linkers = DataCentre.linkers.filter { it.person == person && it.film != null }
        } else if (film != null && person != null) {
            linkers = DataCentre.linkers.filter { it.person == person && it.film == film }
        }
        return linkers
    }

    //List person fragment, images fragment
    fun getGallery(film: Film): Gallery {
        return Gallery(
            images = film.images,
            tabs = film.images.groupingBy { it.imageGroup }.eachCount().toList()
        )
    }

    fun getImageStart(): List<ImageStart> {
        return listOf(
                ImageStart(imageResource = R.drawable.ic_start1, signature = R.string.signature1,
                    imageIndicator = R.drawable.indicator_start_fragment_1),
                ImageStart(imageResource = R.drawable.ic_start2, signature = R.string.signature2,
                    imageIndicator = R.drawable.indicator_start_fragment_2),
                ImageStart(imageResource = R.drawable.ic_start3, signature = R.string.signature3,
                    imageIndicator = R.drawable.indicator_start_fragment_3),
                ImageStart(imageResource = R.drawable.ic_start1, signature = R.string.signature4,
                    imageIndicator = R.drawable.indicator_start_fragment_3),
        )
    }

    suspend fun getFilters(page: Int, kit: Kit): List<Linker> {
        return dataSourceAPI.getFilters(page, kit)
    }

    fun getGenres() = DataCentre.genres

    fun getCountries() = DataCentre.countries

    fun takeFilm() = DataCentre.takeFilm()

    fun putFilm(film: Film) {
        DataCentre.putFilm(film)
    }

    fun putPerson(person: Person) {
        DataCentre.putPerson(person)
    }                // film info, list person fragment

    fun takePerson() = DataCentre.takePerson()

    fun takeKit() = DataCentre.takeKit()

    fun putKit(item: Kit) {
        DataCentre.putKit(item)
    }

    fun takeSearchFilter() = DataCentre.takeSearchFilter()

    fun putSearchFilter(searchFilter: SearchFilter) {
        DataCentre.putSearchFilter(searchFilter)
    }

    fun takeJobPerson() = DataCentre.takeJobPerson()

    fun putJobPerson(item: String) {
        DataCentre.putJobPerson(item)
    }

    // FUNCTION DB #################################################

    fun changeViewed(film: Film) {
        dataSourceDB.setViewed(film)
        film.viewed = !film.viewed
    }
    fun changeFavorite(film: Film) {
        dataSourceDB.addRemoveFilmToCollection(film,1)
        film.favorite = !film.favorite
    }
    fun changeBookmark(film: Film) {
        dataSourceDB.addRemoveFilmToCollection(film,2)
        film.bookmark = !film.bookmark
    }

    fun addFilmToCollection(collection: CollectionDB, film: Film) {
        if (dataSourceDB.getFilm(film) == null) {
            dataSourceDB.addFilm(FilmDB(idFilm = film.filmId!!, msg = "", film))
        }
        addRemoveFilmToCollection(collection, film)
    }
    fun getCollectionsFilm(filmId:Int): List<CollectionDB> {
        val listCollectionFilmDB = dataSourceDB.getCollections()
        if (listCollectionFilmDB.isNotEmpty()) {
            listCollectionFilmDB.forEach {
                it.collection?.count = dataSourceDB.getCountFilmCollection(it.idCollection).count()
                it.collection?.included = dataSourceDB.getFilmInCollection(filmId,it.idCollection)
//                Log.d("KDS", " count = ${it.count}")
            }
        }
        return listCollectionFilmDB
    }

    fun getCollectionsDB(): List<CollectionDB> {
        val listCollectionFilmDB = dataSourceDB.getCollections()
        if (listCollectionFilmDB.isNotEmpty()) {
            listCollectionFilmDB.forEach {
                it.collection?.count = dataSourceDB.getCountFilmCollection(it.idCollection).count()
//                Log.d("KDS", " count = ${it.collection?.count}")
            }
        }
        return listCollectionFilmDB
    }

    fun addCollection(nameCollection: String): CollectionDB? {
        dataSourceDB.addCollection(CollectionDB(collection = Collection( name= nameCollection)))
        return dataSourceDB.getCollectionRecord(nameCollection)
    }

    fun deleteCollection(collection: Collection){
        dataSourceDB.deleteCollection(collection.name)
    }

    fun addRemoveFilmToCollection(collection: CollectionDB, film: Film): List<CollectionDB> {
        dataSourceDB.addRemoveFilmToCollection(film, collection.idCollection)
        return getCollectionsFilm(film.filmId!!)
    }

    fun getViewedFilms() : List<Linker> {
        val linkers = mutableListOf<Linker>()
        val listFilmId = dataSourceDB.getViewedFilmsId()
        listFilmId.forEach { filmId ->
            linkers.add(Linker(film = DataCentre.films.find { it.filmId == filmId },
                kit = Kit.VIEWED))
        }
        //Add item for viewing card "clear history"
        linkers.add(Linker(Film(),null,null,null,Kit.VIEWED))
        return linkers
    }

    fun getFilmsInCollectionName(nameCollection: String = "", idCollection: Int = 0): List<Linker> {
        var idCollect = idCollection
        val linkers = mutableListOf<Linker>()
        if (idCollect == 0){
            idCollect = dataSourceDB.getCollectionRecord(nameCollection)?.idCollection ?: 0
        }
        var kit = Kit.COLLECTION
        if (idCollect == 2) kit = Kit.BOOKMARK
        if (idCollect != 0) {
            val listFilmId = dataSourceDB.getListFilmsIdInCollection(idCollect)
            listFilmId.forEach { filmId ->
                linkers.add( Linker(film = DataCentre.films.find { it.filmId == filmId }, kit = kit))
            }
        }
        linkers.add(Linker( Film(),null,null,null, kit))
        return linkers
    }
    fun clearViewedFilm(){
        dataSourceDB.clearViewedFilm()
    }
    fun clearBookmarkFilm(){
        dataSourceDB.clearBookmarkFilm()
    }

    fun viewedFlow(id: Int): Flow<Boolean> = dataSourceDB.viewedFlow(id)
    fun bookmarkFlow(id: Int): Flow<Boolean> = dataSourceDB.bookmarkFlow(id)
    fun favoriteFlow(id: Int): Flow<Boolean> = dataSourceDB.favoriteFlow(id)
}
//################################################################################################################
