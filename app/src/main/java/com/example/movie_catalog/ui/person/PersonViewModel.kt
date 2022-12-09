package com.example.movie_catalog.ui.person

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()

    init {
        getPerson(App.personDTOApp.staffId!!)
    }

    private var _person = MutableStateFlow( Person(films = filmsStart) )
    var person = _person.asStateFlow()

    private fun getPerson(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                Log.d("KDS", "View model getPerson, start order")
                dataRepository.getPersonInfo(id)
            }.fold(
                onSuccess = {
                    Log.d("KDS", "View model getPerson, send data to fragment")
                    _person.value = it },
                onFailure = { Log.d("Error", it.message ?: "") }
            )
        }
    }

    companion object {

        var filmsStart = listOf(
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(),
        )
    }
}