package com.example.movie_catalog.ui.filmography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmographyData
import com.example.movie_catalog.entity.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmographyViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localPerson: Person? = null

    private var _person = MutableStateFlow(FilmographyData())
    var person = _person.asStateFlow()

    init {
        takePerson()
        localPerson?.let { getImages(it) }
    }

    private fun getImages(localPerson: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            _person.value = dataRepository.getFilmographyData(localPerson)
        }
    }
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }

}