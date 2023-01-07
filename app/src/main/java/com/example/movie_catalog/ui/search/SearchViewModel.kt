package com.example.movie_catalog.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.PagedSourceData
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()

    var pagedFilms: Flow<PagingData<Linker>>

    init {
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.SEARCH) }
        ).flow.cachedIn(viewModelScope)
    }

    fun startSearch(keyWord: String){
        putTextSearch(keyWord)
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.SEARCH) }
        ).flow.cachedIn(viewModelScope)
    }

    fun putTextSearch(keyWord:String){
        if (keyWord.isNotEmpty()) {
            val filter = dataRepository.takeSearchFilter()
            filter.keyWord = keyWord
            dataRepository.putSearchFilter(filter)
        }
    }

    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
}