package com.example.movie_catalog.ui.filmography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.filminfo.Gallery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmographyViewModel @Inject constructor(): ViewModel() {

    private var _person = MutableStateFlow( Person() )
    var person = _person.asStateFlow()

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _person.value = App.personApp
        }
    }
}