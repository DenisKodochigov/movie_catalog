package com.example.movie_catalog.ui.film_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App.Companion.filmApp
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Gallery
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

    private var _similar = MutableStateFlow(listOf<Film>())
    var similar = _similar.asStateFlow()

    init {
        getFilmInfo()
        getPersons()
        getGallery()
        getSimilar()
    }

    private fun getFilmInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getInfoFilmSeason(filmApp.filmId!!)
            }.fold(
                onSuccess = {_filmInfo.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersons(filmApp.filmId!!)
            }.fold(
                onSuccess = {_personDTO.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getGallery() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getGallery(filmApp.filmId!!)
            }.fold(
                onSuccess = {_gallery.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getSimilar() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSimilar(filmApp.filmId!!)
            }.fold(
                onSuccess = {_similar.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
}