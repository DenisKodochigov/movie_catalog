package com.example.movie_catalog.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.api.home.getKit.SelectedKit
import com.example.movie_catalog.entity.ErrorApp
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
class HomeViewModel @Inject constructor(private var dataRepository: DataRepository,
                                        private val errorApp: ErrorApp) : ViewModel() {

    //Random Selection Data Channel
    private var _namekit = MutableStateFlow(SelectedKit())
    var namekit = _namekit.asStateFlow()
    //Premiere movie Data Channel
    private var _premieres = MutableStateFlow(Plug.plugLinkers)
    var premieres = _premieres.asStateFlow()
    //Popular movie Data Channel
    private var _popularFilms = MutableStateFlow(Plug.plugLinkers)
    var popularFilms = _popularFilms.asStateFlow()
    //TOP movie Data Channel
    private var _pageTop250 = MutableStateFlow(Plug.plugLinkers)
    var pageTop250 = _pageTop250.asStateFlow()
    //Random selection 1 movie Data Channel
    private var _randomKit1 = MutableStateFlow(Plug.plugLinkers)
    var randomKit1 = _randomKit1.asStateFlow()
    //Random selection 2 movie Data Channel
    private var _randomKit2 = MutableStateFlow(Plug.plugLinkers)
    var randomKit2 = _randomKit2.asStateFlow()
    //TV series movie Data Channel
    private var _serials = MutableStateFlow(Plug.plugLinkers)
    var serials = _serials.asStateFlow()
    //Requesting data when starting a fragment
    init {
        getGenres()
        getPremieres()
        getPopular()
        getTop250()
        getSerials()
    }
    //Requesting data to select two random movie selections
    private fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getRandomKitName() }.fold(
                //Processing a successful request
                onSuccess = { kit ->
                    //We send it to the UI for display
                    _namekit.value = kit
                    //Converting the received data to send a request for a list of movies
                    Kit.RANDOM1.genreID = kit.genre1.id ?: 0
                    Kit.RANDOM1.countryID = kit.country1.id ?: 0
                    Kit.RANDOM2.genreID = kit.genre2.id ?: 0
                    Kit.RANDOM2.countryID = kit.country2.id ?: 0
                    if (kit.genre1.genre != null || kit.country1.country != null) {
                        Kit.RANDOM1.displayText  =
                            kit.genre1.genre.toString().replaceFirstChar{it.uppercase()}.trim() + " " +
                            kit.country1.country.toString().replaceFirstChar{it.uppercase()}.trim()
                    }

                    if (kit.genre2.genre != null || kit.country2.country != null) {
                        Kit.RANDOM2.displayText =
                            kit.genre2.genre.toString().replaceFirstChar{it.uppercase()}.trim() + " " +
                            kit.country2.country.toString().replaceFirstChar{it.uppercase()}.trim()
                    }
                    //Request for a list of movies by selected parameters
                    getRandom1()
                    getRandom2()
                },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the premieres
    private fun getPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = {_premieres.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the popular
    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1, Kit.POPULAR)
            }.fold(
                onSuccess = {_popularFilms.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the top 250
    private fun getTop250() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getTop(1, Kit.TOP250)
            }.fold(
                onSuccess = {_pageTop250.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the tv series
    private fun getSerials() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilters(1, Kit.SERIALS)
            }.fold(
                onSuccess = {_serials.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the random 1
    private fun getRandom1() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getFilters(1, Kit.RANDOM1) }.fold(
                onSuccess = {_randomKit1.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Request a list of films from the random 2
    private suspend fun getRandom2() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getFilters(1, Kit.RANDOM2) }.fold(
                onSuccess = {_randomKit2.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
    //Save the kit.object for the next fragment
    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }

}

