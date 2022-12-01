package com.example.movie_catalog.ui.kit_films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.App.Companion.kitApp
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.data.repositary.api.PagedSourceAPI
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.Film
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

    private var _premieres = MutableStateFlow(filmsStart)
    var premieres = _premieres.asStateFlow()

    var pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PagedSourceAPI() }
    ).flow.cachedIn(viewModelScope)

    init {
        if (kitApp == Kit.PREMIERES) getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = { _premieres.value = it},
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    companion object {
        var filmsStart = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film()
        )
    }
}