package com.example.movie_catalog.ui.person

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.data.repositary.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor()  : ViewModel() {

    private val dataRepository = DataRepository()

    private var _bestfilm = MutableStateFlow(PersonInfoDTO())
    var bestfilm = _bestfilm.asStateFlow()

    private fun getPerson(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPersonInfo(id)
            }.fold(
                onSuccess = {_bestfilm.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }

    companion object {

        var filmsStart = listOf( Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(), Film(),
            Film(),
        )
    }
}