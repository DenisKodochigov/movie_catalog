package com.example.movie_catalog.ui.film_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App.Companion.filmApp
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.entity.filminfo.FilmInfoSeasons
import com.example.movie_catalog.entity.filminfo.person.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var _filmInfo = MutableStateFlow(FilmInfoSeasons())
    var filmInfo = _filmInfo.asStateFlow()
    private var _actors = MutableStateFlow(listOf<Person>())
    var actors = _actors.asStateFlow()

    init {
        getFilmInfo()
        getActors()
    }

    private fun getFilmInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilmInfo(filmApp.kinopoiskId!!)
            }.fold(
                onSuccess = {_filmInfo.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
    private fun getActors() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getActors(filmApp.kinopoiskId!!)
            }.fold(
                onSuccess = {_actors.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
}