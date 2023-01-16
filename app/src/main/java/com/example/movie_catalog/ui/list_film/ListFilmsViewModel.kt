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
    private var localPerson: Person? = null
    private var localFilm:Film? = null

    private var _listLink = MutableStateFlow(Plug.plugLinkers)
    var listLink = _listLink.asStateFlow()

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
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }
}