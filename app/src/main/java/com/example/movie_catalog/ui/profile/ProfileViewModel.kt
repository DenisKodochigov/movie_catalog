package com.example.movie_catalog.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.Convertor
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

    private var _viewedFilm = MutableStateFlow(listOf<Linker>())
    var viewedFilm = _viewedFilm.asStateFlow()

    private var _bookmarkFilm = MutableStateFlow(listOf<Linker>())
    var bookmarkFilm = _bookmarkFilm.asStateFlow()

    private var _collection = MutableStateFlow(listOf<Collection>())
    var collection = _collection.asStateFlow()

    init {
        getViewedFilm()
        getBookmarkFilm()
        getCollections()
    }

    private fun getViewedFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                 dataRepository.getViewedFilms()
            }.fold(
                onSuccess = { _viewedFilm.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }
    private fun getBookmarkFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getBookmarkFilms()
            }.fold(
                onSuccess = { _bookmarkFilm.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }
    private fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }

    fun putKit(kit: Kit){
        dataRepository.putKit(kit)
    }

    fun deleteCollection(collection: com.example.movie_catalog.entity.Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.deleteCollection(collection)
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") }
            )
        }
    }

    fun clearKit(kit: Kit){
        if (kit == Kit.VIEWED){
            dataRepository.clearViewedFilm()
        } else if (kit == Kit.BOOKMARK){
            dataRepository.clearBookmarkFilm()
        }
    }

    fun newCollection(nameCollection: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.addCollection(nameCollection)
                Convertor().fromCollectionDBtoCollection(dataRepository.getCollectionsDB())
            }.fold(
                onSuccess = { _collection.value = it },
                onFailure = { Log.d("KDS", it.message ?: "getFilmInfo") } )
        }
    }
}