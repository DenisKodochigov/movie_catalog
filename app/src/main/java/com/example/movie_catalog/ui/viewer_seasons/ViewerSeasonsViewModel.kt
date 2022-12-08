package com.example.movie_catalog.ui.viewer_seasons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewerSeasonsViewModel @Inject constructor(): ViewModel() {

    private var _listSeason = MutableStateFlow(InfoFilmSeasons())
    var listSeason = _listSeason.asStateFlow()

    init {
        getSerials()
    }

    private fun getSerials() {
        viewModelScope.launch(Dispatchers.IO) {
            _listSeason.value = App.filmInfoSeasonsApp
        }
    }
}