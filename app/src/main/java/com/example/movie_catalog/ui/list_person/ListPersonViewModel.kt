package com.example.movie_catalog.ui.list_person

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
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
class ListPersonViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    //The film-object that is used on the page
    private var localFilm: Film? = null
    //The job-object that is used on the page
    private var localJob: String? = null
    //Data chanel list person
    private var _listPerson = MutableStateFlow(Plug.plugLinkers)
    var listPerson = _listPerson.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilm()
        takeJobPerson()
        if (localJob !=  null && localFilm != null) getData()
    }
    //Request for a list of persons
    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val listPerson = dataRepository.getPersons(localFilm!!, localJob!!)
            if (listPerson.isNotEmpty()) _listPerson.value = listPerson
            else Toast.makeText(App.context, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
        }
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    //Get a saved job.object
    private fun takeJobPerson() {
        val item = dataRepository.takeJobPerson()
        if (item != null) localJob = item
    }
    //Save the person job.object for the next fragment
    fun putPersonId(person: Person){
        dataRepository.putPerson(person)
    }
}