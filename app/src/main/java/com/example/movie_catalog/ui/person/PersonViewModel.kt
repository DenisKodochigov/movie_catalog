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
    private var localPerson:Person? = null


    private var _linkrers = MutableStateFlow(Plug.plugLinkers)
    var linkrers = _linkrers.asStateFlow()

    init {
        takePerson()
        localPerson?.let { getPerson(it) }
    }

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
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }
    fun putPerson(){
        localPerson?.let { dataRepository.putPerson(it)}
    }

    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
}