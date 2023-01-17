package com.example.movie_catalog.ui.kit_films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.PagedSourceData
import com.example.movie_catalog.entity.ErrorApp
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.plug.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitfilmsViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    //The kit that is displayed on the page
    var localKit: Kit? = null
    //Data chanel for premiere movies
    private var _premieres = MutableStateFlow(Plug.plugLinkers)
    var premieres = _premieres.asStateFlow()
    //Data chanel for paging adapter
    var pagedFilms: Flow<PagingData<Linker>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PagedSourceData(localKit!!) }
    ).flow.cachedIn(viewModelScope)

    init {
        takeKit()
        when(localKit) {
            Kit.PREMIERES -> getPremieres()
            Kit.VIEWED -> getDataViewed()
            Kit.BOOKMARK -> getDataCollection("",2)
            Kit.COLLECTION -> getDataCollection(localKit!!.nameKit)
            else -> {}
        }
    }
    //Request for a list of movies from the collection
    private fun getDataCollection(nameCollection: String = "", idCollection: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilmsInCollectionName(nameCollection, idCollection)
            }.fold(
                onSuccess = { _premieres.value = it},
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
    //Request for a list of viewed movies
    private fun getDataViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getViewedFilms()
            }.fold(
                onSuccess = { _premieres.value = it},
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
    //Request for a list of premiers
    private fun getPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = { _premieres.value = it},
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Get a saved kit object
    private fun takeKit(){
        val kit = dataRepository.takeKit()
        if (kit != null) localKit = kit
    }
}