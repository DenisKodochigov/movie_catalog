package com.example.movie_catalog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.Kit
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

    private var _namekit = MutableStateFlow(SelectedKit())
    var namekit = _namekit.asStateFlow()

    private var _premieres = MutableStateFlow(Plug.plugLinkers)
    var premieres = _premieres.asStateFlow()

    private var _popularFilms = MutableStateFlow(Plug.plugLinkers)
    var popularFilms = _popularFilms.asStateFlow()

    private var _pageTop250 = MutableStateFlow(Plug.plugLinkers)
    var pageTop250 = _pageTop250.asStateFlow()

    private var _randomKit1 = MutableStateFlow(Plug.plugLinkers)
    var randomKit1 = _randomKit1.asStateFlow()

    private var _randomKit2 = MutableStateFlow(Plug.plugLinkers)
    var randomKit2 = _randomKit2.asStateFlow()

    private var _serials = MutableStateFlow(Plug.plugLinkers)
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
            kotlin.runCatching { dataRepository.getGenres() }.fold(
                onSuccess = { kit ->
                    _namekit.value = kit
                    Kit.RANDOM1.genreID = kit.genre1.id ?: 0
                    Kit.RANDOM1.countryID = kit.country1.id ?: 0
                    Kit.RANDOM2.genreID = kit.genre2.id ?: 0
                    Kit.RANDOM2.countryID = kit.country2.id ?: 0
                    if (kit.genre1.genre != null || kit.country1.country != null) {
                        Kit.RANDOM1.nameKit  =
                            kit.genre1.genre.toString().replaceFirstChar{it.uppercase()}.trim() + " " +
                            kit.country1.country.toString().replaceFirstChar{it.uppercase()}.trim()
                    }

                    if (kit.genre2.genre != null || kit.country2.country != null) {
                        Kit.RANDOM2.nameKit =
                            kit.genre2.genre.toString().replaceFirstChar{it.uppercase()}.trim() + " " +
                            kit.country2.country.toString().replaceFirstChar{it.uppercase()}.trim()
                    }
                    getRandom1()
                    getRandom2()

                },
                onFailure = { Log.d("KDS",it.message ?: "getGenres")}
            )
        }
    }

    private fun getPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = {_premieres.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getPremieres")}
            )
        }
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1, Kit.POPULAR)
            }.fold(
                onSuccess = {_popularFilms.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getPopular")}
            )
        }
    }

    private fun getTop250() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1, Kit.TOP250)
            }.fold(
                onSuccess = {_pageTop250.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getTop250")}
            )
        }
    }

    private fun getSerials() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getSerials(1, Kit.SERIALS)
            }.fold(
                onSuccess = {_serials.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getSerials")}
            )
        }
    }

    private fun getRandom1() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getFilters(1, Kit.RANDOM1) }.fold(
                onSuccess = {_randomKit1.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getRandom1")}
            )
        }
    }

    private fun getRandom2() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getFilters(1, Kit.RANDOM2) }.fold(
                onSuccess = {_randomKit2.value = it },
                onFailure = { Log.d("KDS",it.message ?: "getRandom2")}
            )
        }
    }

    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }

    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }

}

