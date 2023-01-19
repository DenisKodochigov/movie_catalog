package com.example.movie_catalog.ui.season

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
class SeasonsViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilm: Film? = null
    //Data chanel for tv series
    private var _listSeason = MutableStateFlow(Film())
    var listSeason = _listSeason.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilm()
        localFilm?.let{ getSerials(it) }
    }
    //Requesting a list of TV series
    private fun getSerials(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            _listSeason.value = dataRepository.getInfoFilmSeason(film)!!
        }
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
}