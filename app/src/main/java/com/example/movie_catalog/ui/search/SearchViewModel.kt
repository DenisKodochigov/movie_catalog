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
class SearchViewModel @Inject constructor(private var dataRepository: DataRepository): ViewModel() {

//    private val dataRepository = DataRepository()
    //Data chanel for paging adapter
    var pagedFilms: Flow<PagingData<Linker>>

    init {
        //Request when starting a fragment
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.SEARCH,dataRepository) }
        ).flow.cachedIn(viewModelScope)
    }
    //Request after updating the search parameters
    fun startSearch(keyWord: String){
        putTextSearch(keyWord)
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.SEARCH, dataRepository) }
        ).flow.cachedIn(viewModelScope)
    }
    //Save the set search parameters
    fun putTextSearch(keyWord:String){
        if (keyWord.isNotEmpty()) {
            val filter = dataRepository.takeSearchFilter()
            filter.keyWord = keyWord
            dataRepository.putSearchFilter(filter)
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
}