package com.example.movie_catalog.ui.filmography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmographyViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localPersonId: Int? = null

    private var _person = MutableStateFlow( Person() )
    var person = _person.asStateFlow()

    init {
        takePersonId()
        if (localPersonId != null) getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _person.value = dataRepository.takePersonFilmography(localPersonId!!)
        }
    }
    fun putFilmId(item: Int){
        dataRepository.putFilmId(item)
    }
    private fun takePersonId(){
        val item = dataRepository.takePersonId()
        if (item != null) localPersonId = item
    }

}