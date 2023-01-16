package com.example.movie_catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.ErrorApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()

    init {
        getConditionWork()
    }
    fun getConditionWork(): Boolean {
        var conditionWork: Boolean? = null
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.synchronizationDataCenterAndDB()
                dataRepository.getConditionWork()
            }.fold(
                onSuccess = {conditionWork = it },
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
        while (conditionWork == null) {}
        return conditionWork as Boolean
    }
}