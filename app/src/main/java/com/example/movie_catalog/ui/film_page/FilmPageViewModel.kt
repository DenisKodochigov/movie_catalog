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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilm:Film? = null

    private var _filmInfo = MutableStateFlow(Film())
    var filmInfo = _filmInfo.asStateFlow()

    private var _person = MutableStateFlow(listOf<Linker>())
    var person = _person.asStateFlow()

    private var _images = MutableStateFlow(listOf<ImageFilm>())
    var images = _images.asStateFlow()

    private var _similar = MutableStateFlow(listOf<Linker>())
    var similar = _similar.asStateFlow()

    private var _collections = MutableStateFlow(listOf<CollectionDB>())
    var collections = _collections.asStateFlow()

    lateinit var viewed: StateFlow<Boolean>
    lateinit var favorite: StateFlow<Boolean>
    lateinit var bookmark: StateFlow<Boolean>

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

    private fun getFilmInfo(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getInfoFilmSeason(film)
            }.fold(
                onSuccess = { if (it != null) _filmInfo.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

    private fun getPersons(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersons(film)
            }.fold(
                onSuccess = { _person.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

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

    private fun getSimilar(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSimilar(film)
            }.fold(
                onSuccess = { _similar.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }

    fun clickViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeViewed(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

    fun clickFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeFavorite(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

    fun clickBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeBookmark(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }

    fun putFilm(){
        localFilm?.let { dataRepository.putFilm(it) }
    }

    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }

    fun putPerson(person: Person) {
        dataRepository.putPerson(person)
    }

    fun putJobPerson(item: String){
        dataRepository.putJobPerson(item)
    }
}