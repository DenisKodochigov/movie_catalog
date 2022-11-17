package com.example.movie_catalog.ui.film_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App.Companion.filmApp
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.entity.FilmInfoSeasons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmInfoViewModel : ViewModel() {

    private val dataRepository = DataRepository()
    private var _filmInfo = MutableStateFlow(FilmInfoSeasons())
    var filmInfo = _filmInfo.asStateFlow()

    init {
        getFilmInfo()
    }

    private fun getFilmInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilmInfo(filmApp.kinopoiskId!!)
            }.fold(
                onSuccess = {_filmInfo.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
}