package com.example.movie_catalog.ui.full_list_films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.repositary.DataRepository
import com.example.movie_catalog.data.repositary.api.home.premieres.PremieresDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListfilmsViewModel @Inject constructor(): ViewModel() {
    private val dataRepository = DataRepository()

    private var _premieresDTO = MutableStateFlow(PremieresDTO())
    var premieres = _premieresDTO.asStateFlow()
    private var _premieresLoading = MutableStateFlow(false)
    var premieresLoading = _premieresLoading.asStateFlow()

    init {
        getPremieres()
    }

    private fun getPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = {_premieresDTO.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
}