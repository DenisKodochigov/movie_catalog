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
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
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
    var localKit: Kit? = null

    private var _premieres = MutableStateFlow(Plug.plugLinkers)
    var premieres = _premieres.asStateFlow()

    var pagedFilms: Flow<PagingData<Linker>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PagedSourceData(localKit!!) }
    ).flow.cachedIn(viewModelScope)

    init {
        takeKit()
        if (localKit == Kit.PREMIERES) getData()
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
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    private fun takeKit(){
        val kit = dataRepository.takeKit()
        if (kit != null) localKit = kit
    }
}