package com.example.movie_catalog.ui.film_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
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

//    private var _viewed = MutableStateFlow(false)
//    var viewed = _viewed.asStateFlow()
//    private var _favorite = MutableStateFlow(false)
//    var favorite = _favorite.asStateFlow()
//    private var _bookmark = MutableStateFlow(false)
//    var bookmark = _bookmark.asStateFlow()

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
                        dataRepository.getCollections(it)
                    }}
            }.fold(
                onSuccess = { if (it != null) _collections.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }

    fun newCollection(nameCollection: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                localFilm?.let { film ->
                    dataRepository.newCollection(nameCollection, film)
                }
            }.fold(
                onSuccess = { if (it != null) _collections.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") } )
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
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }

    private fun getFilmInfo(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getInfoFilmSeason(film)
            }.fold(
                onSuccess = { if (it != null) _filmInfo.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }

    private fun getPersons(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersons(film)
            }.fold(
                onSuccess = { _person.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getPersons") }
            )
        }
    }

    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getImages(film)
            }.fold(
                onSuccess = { _images.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getImages") }
            )
        }
    }

    private fun getSimilar(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSimilar(film)
            }.fold(
                onSuccess = { _similar.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getSimilar") }
            )
        }
    }

    fun clickViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeViewed(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { Log.d("KDS", it.message ?: "clickViewed") }
            )
        }
    }

    fun clickFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeFavorite(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { Log.d("KDS", it.message ?: "clickFavorite") }
            )
        }
    }

    fun clickBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.changeBookmark(localFilm!!) }.fold(
                onSuccess = {},
                onFailure = { Log.d("KDS", it.message ?: "clickBookmark") }
            )
        }
    }

    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }

    fun putFilm(){
        localFilm?.let { dataRepository.putFilm(it) }
    }

    fun putPerson(person: Person) {
        dataRepository.putPerson(person)
    }

    fun putJobPerson(item: String){
        dataRepository.putJobPerson(item)
    }
}