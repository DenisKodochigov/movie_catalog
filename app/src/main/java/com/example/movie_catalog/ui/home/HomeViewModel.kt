package com.example.movie_catalog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.repositary.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()

    private var _genreMap = MutableStateFlow<Map<String, String>>(emptyMap())
    var genreMap = _genreMap.asStateFlow()

    private var _premieres = MutableStateFlow(premieresDTOStart)
    var premieres = _premieres.asStateFlow()

    private var _popularFilms = MutableStateFlow(topFilmsStartDTO)
    var popularFilms = _popularFilms.asStateFlow()

    private var _pageTop250 = MutableStateFlow(topFilmsStartDTO)
    var pageTop250 = _pageTop250.asStateFlow()

    init {
        getGenres()
        getPremieres()
        getPopular()
        getTop250()
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

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPopular()
            }.fold(
                onSuccess = {_popularFilms.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    private fun getTop250() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPopular()
            }.fold(
                onSuccess = {_pageTop250.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    companion object {

        var premieresDTOStart = PremieresDTO(total = 20, items = mutableListOf( FilmDTO(), FilmDTO(),  FilmDTO(),
                    FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(),
                    FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(), FilmDTO(),))

        var topFilmsStartDTO = TopFilms(pagesCount = 1, films = mutableListOf( TopFilmDTO(), TopFilmDTO(),
                    TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(),
                    TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(),
                    TopFilmDTO(), TopFilmDTO(), TopFilmDTO(), TopFilmDTO(),))
    }
}

//    var pageTopFilm: Flow<PagingData<TopFilm>> = Pager(
//        config = PagingConfig(pageSize = 20),
//        pagingSourceFactory = { TopFilmsPagedSource("TOP_100_POPULAR_FILM") }
//    ).flow.cachedIn(viewModelScope)
//
//    var pageTop250: Flow<PagingData<TopFilm>> = Pager(
//        config = PagingConfig(pageSize = 20),
//        pagingSourceFactory = { TopFilmsPagedSource("TOP_250_BEST_FILMS") }
//    ).flow.cachedIn(viewModelScope)
