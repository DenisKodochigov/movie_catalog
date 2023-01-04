package com.example.movie_catalog.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.PagedSourceData
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.SearchFilter
import com.example.movie_catalog.entity.enumApp.Kit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()

//    var filter = SearchFilter()
//    var genres = emptyList<GenreIdDTO>()
//    var countries = emptyList<CountryIdDTO>()

    var pagedFilms: Flow<PagingData<Linker>>

//    var pagesFilmFilter

    init {
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.ALL) }
        ).flow.cachedIn(viewModelScope)
    }

    fun startSearch(){
        pagedFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSourceData(Kit.ALL) }
        ).flow.cachedIn(viewModelScope)
    }

    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
}