package com.example.movie_catalog.ui.person

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
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
//
//    private var _person = MutableStateFlow(Plug.listLinks )
//    var person = _person.asStateFlow()

    private var _linkrers = MutableStateFlow(emptyList<Linker>() )
    var linkrers = _linkrers.asStateFlow()

    init {
        takePerson()
        localPerson?.let { getPerson(it) }
    }

    private fun getPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                Log.d("KDS", "View model getPerson, start order. Person: ${person.nameRu}")
                dataRepository.getPersonInfo(person)
            }.fold(
                onSuccess = {
                    Log.d("KDS", "View model getPerson, send data to fragment")
                    _linkrers.value = it },
                onFailure = {
                    Log.d("Error", it.message ?: "PersonViewModel.getPersonInfo()")
                }
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