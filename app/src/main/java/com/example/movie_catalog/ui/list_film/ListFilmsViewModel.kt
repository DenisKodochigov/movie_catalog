package com.example.movie_catalog.ui.list_film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.plug.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFilmsViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    //The person object that is used on the page
    private var localPerson: Person? = null
    //The film object that is used on the page
    private var localFilm:Film? = null
    //Data chanel a list for show
    private var _listLink = MutableStateFlow(Plug.plugLinkers)
    var listLink = _listLink.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilm()
        takePerson()
        if (localFilm != null || localPerson != null) getData(localFilm, localPerson)
    }

    private fun getData(film: Film?, person: Person?) {

        viewModelScope.launch(Dispatchers.IO) {
            _listLink.value = dataRepository.getLinkersForListFilmFragment(film, person)
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    //Get a saved person object
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }
}