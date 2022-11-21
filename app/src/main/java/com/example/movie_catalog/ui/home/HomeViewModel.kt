package com.example.movie_catalog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.data.repositary.api.TopFilmsPagedSource
import com.example.movie_catalog.entity.home.premieres.Film
import com.example.movie_catalog.entity.home.premieres.Premieres
import com.example.movie_catalog.entity.home.top.TopFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()

    private var _genreMap = MutableStateFlow<Map<String, String>>(emptyMap())
    var genreMap = _genreMap.asStateFlow()
    private var _premieres = MutableStateFlow(premieresStart)
    var premieres = _premieres.asStateFlow()

    var pageTopFilm: Flow<PagingData<TopFilm>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TopFilmsPagedSource("TOP_100_POPULAR_FILM") }
    ).flow.cachedIn(viewModelScope)

    var pageTop250: Flow<PagingData<TopFilm>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TopFilmsPagedSource("TOP_250_BEST_FILMS") }
    ).flow.cachedIn(viewModelScope)

    init {
        getGenres()
        getPremieres()
    }

    private fun getGenres() {

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getGenres()
            }.fold(
                onSuccess = {_genreMap.value = it },
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

    companion object {
        var listFilm = mutableListOf(
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),
            Film(),)
        var premieresStart = Premieres(total = 20, items = listFilm)

    }
}