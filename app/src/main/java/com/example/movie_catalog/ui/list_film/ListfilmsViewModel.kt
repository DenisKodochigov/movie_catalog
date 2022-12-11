package com.example.movie_catalog.ui.list_film

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListfilmsViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()

    private var _listFilms = MutableStateFlow(filmsStart)
    var listFilms = _listFilms.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (dataRepository.takeListFilm().isNotEmpty()) {
                dataRepository.takeListFilm().forEach { film ->
                    if ( film.posterUrlPreview == null && film.filmId != null){
                        Log.d("KDS","Order for film: ${film.nameRu}")
                        film.posterUrlPreview =
                            dataRepository.getInfoFilmSeason( film.filmId ).infoFilm?.posterUrlPreview
                    }
                }
                _listFilms.value = dataRepository.takeListFilm()
            }
        }
    }
    fun putFilm(item:Film){
        dataRepository.putFilm(item)
    }
    companion object {
        var filmsStart = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film()
        )
    }
}