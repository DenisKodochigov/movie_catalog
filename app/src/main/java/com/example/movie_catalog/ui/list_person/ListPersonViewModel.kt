package com.example.movie_catalog.ui.list_person

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPersonViewModel @Inject constructor() : ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilmId: Int? = null
    private var localJob: String? = null

    private var _listPerson = MutableStateFlow(personStart)
    var listPerson = _listPerson.asStateFlow()

    init {
        takeFilmId()
        takeJobPerson()
        if (localJob !=  null && localFilmId != null) getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val listPerson = dataRepository.getListPerson(localFilmId!!, localJob!!)
            if (listPerson.isNotEmpty()) _listPerson.value = listPerson
            else Toast.makeText(App.context, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
        }
    }

    private fun takeFilmId() {
        val filmId = dataRepository.takeFilmId()
        if (filmId != null) localFilmId = filmId
    }

    private fun takeJobPerson() {
        val item = dataRepository.takeJobPerson()
        if (item != null) localJob = item
    }

    fun putPersonId(id: Int){
        dataRepository.putPersonId(id)
    }

    companion object {
        var personStart = listOf(
            Person(), Person(), Person(), Person(), Person(),
            Person(), Person(), Person(), Person(), Person(), Person(),
            Person(), Person(), Person(), Person(), Person(), Person(),
            Person(), Person()
        )
    }
}