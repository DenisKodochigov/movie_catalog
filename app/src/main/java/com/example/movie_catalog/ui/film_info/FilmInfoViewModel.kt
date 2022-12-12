package com.example.movie_catalog.ui.film_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.filminfo.Images
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var _filmInfo = MutableStateFlow(InfoFilmSeasons())
    var filmInfo = _filmInfo.asStateFlow()

    private var _personDTO = MutableStateFlow(listOf<PersonDTO>())
    var person = _personDTO.asStateFlow()

    private var _gallery = MutableStateFlow(Gallery())
    var gallery = _gallery.asStateFlow()

    private var _images = MutableStateFlow<List<Images>>(emptyList())
    var images = _images.asStateFlow()

    private var _similar = MutableStateFlow(listOf<Film>())
    var similar = _similar.asStateFlow()

    init {
        getFilmInfo()
//        getPersons()
//        getGallery()
//        getSimilar()
    }

    private fun getFilmInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getInfoFilmSeason( dataRepository.takeFilm().filmId!!)
            }.fold(
                onSuccess = {_filmInfo.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersons(dataRepository.takeFilm().filmId!!)
            }.fold(
                onSuccess = {_personDTO.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getGallery() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getImages()
            }.fold(
                onSuccess = {_images.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getSimilar() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSimilar(dataRepository.takeFilm().filmId!!)
            }.fold(
                onSuccess = {_similar.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    fun putFilm(item:Film){
        dataRepository.putFilm(item)
    }
    fun putListFilm(item:List<Film>){
        dataRepository.putListFilm(item)
    }
    fun putGallery(item:Gallery){
        dataRepository.putGallery(item)
    }
//    fun putImage(item: MutableList<FilmImageUrlDTO>){
//        dataRepository.putImage(item)
//    }
    fun putListPersonDTO(item:List<PersonDTO> ){
        dataRepository.putListPersonDTO(item)
    }
    fun putFilmInfoSeasons(item:InfoFilmSeasons){
        dataRepository.putFilmInfoSeasons(item)
    }
    fun putPersonDTO(item:PersonDTO){
        dataRepository.putPersonDTO(item)
    }
    fun clickViewed(){
        val filmId = dataRepository.takeFilm().filmId
        if ( filmId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching { dataRepository.changeViewed(filmId!!) }.fold(
                    onSuccess = {},
                    onFailure = { Log.d("KDS", it.message ?: "") }
                )
            }
        }
    }
    fun clickFavorite(){
        val filmId = dataRepository.takeFilm().filmId
        if ( filmId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching { dataRepository.changeFavorite(filmId!!) }.fold(
                    onSuccess = {},
                    onFailure = { Log.d("KDS", it.message ?: "") }
                )
            }
        }
    }
    fun clickBookmark(){
        val filmId = dataRepository.takeFilm().filmId
        if ( filmId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching { dataRepository.changeBookmark(filmId) }.fold(
                    onSuccess = {},
                    onFailure = { Log.d("KDS", it.message ?: "") }
                )
            }
        }
    }
}