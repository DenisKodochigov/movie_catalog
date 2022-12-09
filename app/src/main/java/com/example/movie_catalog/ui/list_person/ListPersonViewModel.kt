package com.example.movie_catalog.ui.list_film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.api.film_info.PersonDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPersonViewModel @Inject constructor(): ViewModel() {

    private var _listPerson = MutableStateFlow(personStart)
    var listPerson = _listPerson.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (App.listPersonDTOApp.isEmpty()) _listPerson.value = App.listPersonDTOApp
        }
    }

    companion object {
        var personStart = listOf( PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(),
            PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(),
            PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(), PersonDTO(),
            PersonDTO(), PersonDTO()
        )
    }
}