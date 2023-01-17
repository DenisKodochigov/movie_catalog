package com.example.movie_catalog.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.ErrorApp
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
class PersonViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    //The person that is used on the page
    private var localPerson:Person? = null
    //Data chanel movie list
    private var _linkrers = MutableStateFlow(Plug.plugLinkers)
    var linkrers = _linkrers.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takePerson()
        localPerson?.let { getPerson(it) }
    }
    //Request for a person
    private fun getPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersonInfo(person)
            }.fold(
                onSuccess = { _linkrers.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a saved person object
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }
    //Save the person.object for the next fragment
    fun putPerson(){
        localPerson?.let { dataRepository.putPerson(it)}
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
}