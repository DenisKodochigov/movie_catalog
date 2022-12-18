package com.example.movie_catalog.ui.film_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.ImageFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        takeFilm()
        localFilm?.let {
            getFilmInfo(it)
//            getImages(it)
//            getSimilar(it)
//            getPersons(it)
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


//    private val dataRepository = DataRepository()
//    private var localFilmId:Int? = null
//
//    private var _filmInfo = MutableStateFlow(Film())// MutableStateFlow(InfoFilmSeasons())
//    var filmInfo = _filmInfo.asStateFlow()
//
//    private var _personDTO = MutableStateFlow(listOf<Person>())
//    var person = _personDTO.asStateFlow()
//
//    private var _images = MutableStateFlow<List<Images>>(emptyList())
//    var images = _images.asStateFlow()
//
//    private var _similar = MutableStateFlow(listOf<Film>())
//    var similar = _similar.asStateFlow()
//
//    init {
//        takeFilmId()
//        localFilmId?.let {
//            getFilmInfo(it)
//            getImages(it)
//            getSimilar(it)
//            getPersons(it)
//        }
//    }
//
//    private fun getFilmInfo(idFilm: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                dataRepository.getInfoFilmSeason(idFilm)
//            }.fold(
//                onSuccess = { if (it != null) _filmInfo.value = it },
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    private fun getPersons(idFilm: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                dataRepository.getPersons(idFilm)
//            }.fold(
//                onSuccess = { _personDTO.value = it },
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    private fun getImages(idFilm: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                dataRepository.getImages(idFilm)
//            }.fold(
//                onSuccess = { _images.value = it },
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    private fun getSimilar(idFilm: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                dataRepository.getSimilar(idFilm)
//            }.fold(
//                onSuccess = { _similar.value = it },
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    fun clickViewed() {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.changeViewed(localFilmId!!) }.fold(
//                onSuccess = {},
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    fun clickFavorite() {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.changeFavorite(localFilmId!!) }.fold(
//                onSuccess = {},
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    fun clickBookmark() {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.changeBookmark(localFilmId!!) }.fold(
//                onSuccess = {},
//                onFailure = { Log.d("KDS", it.message ?: "") }
//            )
//        }
//    }
//
//    private fun takeFilmId() {
//        val filmId = dataRepository.takeFilmId()
//        if (filmId != null) localFilmId = filmId
//    }
//
//    fun putFilmId(){
//        localFilmId?.let { dataRepository.putFilmId(it) }
//    }
//
//    fun putFilmsSimilar(){
//        dataRepository.putFilmsSimilar(localFilmId!!)
//    }
//
//    fun putPersonId(item: Int) {
//        dataRepository.putPersonId(item)
//    }
//
//    fun putJobPerson(item: String){
//        dataRepository.putJobPerson(item)
//    }
}