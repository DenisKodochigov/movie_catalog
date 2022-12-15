package com.example.movie_catalog.ui.viewer_seasons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerSeasonsViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilmId: Int? = null
    private var _listSeason = MutableStateFlow(Film())
    var listSeason = _listSeason.asStateFlow()

    init {
        takeFilmId()
        localFilmId?.let{ getSerials(it) }
    }

    private fun getSerials(filmId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _listSeason.value = dataRepository.getInfoFilmSeason(filmId)!!
        }
    }
    private fun takeFilmId() {
        val filmId = dataRepository.takeFilmId()
        if (filmId != null) localFilmId = filmId
    }
}