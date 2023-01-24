package com.example.movie_catalog.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.*
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.enumApp.Kit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    //Data chanel a list viewed films
    private var _viewedFilm = MutableStateFlow(listOf<Linker>())
    var viewedFilm = _viewedFilm.asStateFlow()
    //Data chanel a list bookmark films
    private var _bookmarkFilm = MutableStateFlow(listOf<Linker>())
    var bookmarkFilm = _bookmarkFilm.asStateFlow()
    //Data chanel a list films for collection
    private var _collection = MutableStateFlow(listOf<Collection>())
    var collection = _collection.asStateFlow()
    //Requesting data when starting a fragment
    init {
        refreshView()
    }
    //Updating data on the page
    fun refreshView(){
        getViewedFilm()
        getBookmarkFilm()
        getCollections()
    }
    //Get a list of watched movies
    private fun getViewedFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                 dataRepository.getViewedFilms(App.context.getString(R.string.viewed_kit))
            }.fold(
                onSuccess = { _viewedFilm.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a list of movies from bookmark
    private fun getBookmarkFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilmsInCollectionName(App.context.getString(R.string.bookmark_kit))
            }.fold(
                onSuccess = { _bookmarkFilm.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a list of movies from collection
    private fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Save the kit.object for the next fragment
    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }
    // Delete collection
    fun deleteCollection(collection: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                //Deleting collection by name and back a new list of collection
                dataRepository.deleteCollection(collection)
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Deleting all films form bookmark collection
    fun clearCollection(kit: Kit){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.clearBookmarkFilm()
                getBookmarkFilm()
                getCollections()
            }.fold(
                onSuccess = { },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    // Clear the viewed attribute for all movies
    fun clearViewed(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.clearViewedFilm()
                getViewedFilm()
                getCollections()
            }.fold(
                onSuccess = { },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Create a new collection
    fun newCollection(nameCollection: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.addCollection(nameCollection)
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) } )
        }
    }
}