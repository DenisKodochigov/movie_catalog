package com.example.movie_catalog.ui.kit_films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.PagedSourceData
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
    var currentKit: Kit? = null

    private var _premieres = MutableStateFlow(plugFilm)
    var premieres = _premieres.asStateFlow()

    var pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PagedSourceData(currentKit!!) }
    ).flow.cachedIn(viewModelScope)

    init {
        takeKit()
        if (currentKit == Kit.PREMIERES) getData()
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
    fun putFilmId(item:Int){
        dataRepository.putFilmId(item)
    }
    private fun takeKit(){
        val kit = dataRepository.takeKit()
        if (kit != null) currentKit = kit
    }

    companion object {
        var plugFilm = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film()
        )
    }
}