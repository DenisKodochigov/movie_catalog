package com.example.movie_catalog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.plug.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()

    private var _genreMap = MutableStateFlow(SelectedKit())
    var genreMap = _genreMap.asStateFlow()

    private var _premieres = MutableStateFlow(Plug().filmPlug)
    var premieres = _premieres.asStateFlow()

    private var _popularFilms = MutableStateFlow(filmsStart)
    var popularFilms = _popularFilms.asStateFlow()

    private var _pageTop250 = MutableStateFlow(filmsStart)
    var pageTop250 = _pageTop250.asStateFlow()

    private var _randomKit1 = MutableStateFlow(filmsStart)
    var randomKit1 = _randomKit1.asStateFlow()

    private var _randomKit2 = MutableStateFlow(filmsStart)
    var randomKit2 = _randomKit2.asStateFlow()

    private var _serials = MutableStateFlow(filmsStart)
    var serials = _serials.asStateFlow()

    init {
        getGenres()
        getPremieres()
        getPopular()
        getTop250()
        getSerials()
    }

    private fun getGenres() {

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getGenres()
            }.fold(
                onSuccess = {
                                _genreMap.value = it
                                getRandom1(it.genre1.id!!, it.country1.id!!)
                                getRandom2(it.genre2.id!!, it.country2.id!!)
                            },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = {_premieres.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1,Kit.POPULAR)
            }.fold(
                onSuccess = {_popularFilms.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getTop250() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1, Kit.TOP250)
            }.fold(
                onSuccess = {_pageTop250.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getSerials() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSerials(1, Kit.SERIALS)
            }.fold(
                onSuccess = {_serials.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getRandom1(genreId:Int, countryId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilters(1,genreId,countryId,Kit.RANDOM1)
            }.fold(
                onSuccess = {_randomKit1.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getRandom2(genreId:Int, countryId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilters(1,genreId,countryId,Kit.RANDOM2)
            }.fold(
                onSuccess = {_randomKit2.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    fun putKit(item: Kit){
        dataRepository.putKit(item)
    }
    fun putFilm(item:Film){
        dataRepository.putFilm(item)
    }
    companion object {

        var filmsStart = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(),
        )
    }
}

