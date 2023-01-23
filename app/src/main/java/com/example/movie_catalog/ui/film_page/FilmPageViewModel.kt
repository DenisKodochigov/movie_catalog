package com.example.movie_catalog.ui.film_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.entity.ErrorApp
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.filminfo.ImageFilm
import com.example.movie_catalog.entity.plug.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    //The movie that is displayed on the page
    private var localFilm:Film? = null
    //A stream for displaying complete information about the movie
    private var _filmInfo = MutableStateFlow(Film())
    var filmInfo = _filmInfo.asStateFlow()
    //Stream for displaying a list of people who worked on the film
    private var _person = MutableStateFlow(listOf<Linker>())
    var person = _person.asStateFlow()
    //Photo output stream
    private var _images = MutableStateFlow(listOf<ImageFilm>())
    var images = _images.asStateFlow()
    //Stream for a list of similar movies
    private var _similar = MutableStateFlow(Plug.plugLinkers)
    var similar = _similar.asStateFlow()
    //A stream for getting collections and information about them
    private var _collections = MutableStateFlow(listOf<CollectionDB>())
    var collections = _collections.asStateFlow()
    //Stream of viewed state changes
    lateinit var viewed: StateFlow<Boolean>
    //Stream of favorite state changes
    lateinit var favorite: StateFlow<Boolean>
    //Stream of bookmark state changes
    lateinit var bookmark: StateFlow<Boolean>
    //Requesting data when starting a fragment
    init {
        takeFilm()
        localFilm?.let {
            getFlowIcon(it.filmId)
            getFilmInfo(it)
            getImages(it)
            getSimilar(it)
            getPersons(it)
            getCollections()
        }
    }
    //Request for streams for icons
    private fun getFlowIcon(id: Int?){
        id?.let { filmId ->
            viewed = dataRepository.viewedFlow(filmId).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = false
            )
            favorite = dataRepository.favoriteFlow(filmId).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = false
            )
            bookmark = dataRepository.bookmarkFlow(filmId).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = false
            )
        }
    }
    //Requesting Collections
    fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                localFilm?.let { film ->
                    film.filmId?.let {
                        dataRepository.getCollectionsFilm(it)
                    }}
            }.fold(
                onSuccess = { if (it != null) _collections.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Adding a new collection
    fun newCollection(nameCollection: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                localFilm?.let { film ->
                    val collection = dataRepository.addCollection(nameCollection)
                    dataRepository.addFilmToCollection( collection!!, film )
                    dataRepository.getCollectionsFilm(film.filmId!!)
                }
            }.fold(
                onSuccess = { if (it != null) _collections.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) } )
        }
    }
    //Adding (deleting) a movie to (from) a collection
    fun addRemoveFilmToCollection(collection: CollectionDB){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                localFilm?.let { film ->
                    dataRepository.addRemoveFilmToCollection(collection, film)
                }
            }.fold(
                onSuccess = { if (it != null) _collections.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a movie to display
    private fun getFilmInfo(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getInfoFilmSeason(film)
            }.fold(
                onSuccess = { if (it != null) _filmInfo.value = it },
                onFailure = { ErrorApp().errorApi(it.message ?: "Error nothing") }
            )
        }
    }
    //Get a list of persons to display
    private fun getPersons(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersons(film)
            }.fold(
                onSuccess = { _person.value = it },
                onFailure = { ErrorApp().errorApi(it.message ?: "Error nothing") }
            )
        }
    }
    //Get a list of images to display
    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getImages(film)
            }.fold(
                onSuccess = { _images.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a list of similar movie to display
    private fun getSimilar(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getListLinkerForSimilar(film)
            }.fold(
                onSuccess = { _similar.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    //Practice clicking on the viewed icon
    fun clickViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeViewed(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Practice clicking on the favorite icon
    fun clickFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeFavorite(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Practice clicking on the bookmark icon
    fun clickBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeBookmark(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(){
        localFilm?.let { dataRepository.putFilm(it) }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Save the kit.object for the next fragment
    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }
    //Save the person.object for the next fragment
    fun putPerson(person: Person) {
        dataRepository.putPerson(person)
    }
    //Save the personJob.object for the next fragment
    fun putJobPerson(item: String){
        dataRepository.putJobPerson(item)
    }
}