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
class FilmographyViewModel @Inject constructor(private var dataRepository: DataRepository): ViewModel() {

//    private val dataRepository = DataRepository()
    //The person that is displayed on the page
    private var localPerson: Person? = null
    //Stream for displaying a list of movie for person
    private var _person = MutableStateFlow(FilmographyData())
    var person = _person.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takePerson()
        localPerson?.let { getImages(it) }
    }
    //Get a list of images to display
    private fun getImages(localPerson: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            _person.value = dataRepository.getFilmographyData(localPerson)
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Get a saved person object
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }

}