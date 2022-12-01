package com.example.movie_catalog.ui.list_film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.entity.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListfilmsViewModel @Inject constructor(): ViewModel() {

    private var _listFilms = MutableStateFlow(filmsStart)
    var listFilms = _listFilms.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (App.listFilmApp.isEmpty()) _listFilms.value = App.listFilmApp
        }
    }

    companion object {
        var filmsStart = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film()
        )
    }
}