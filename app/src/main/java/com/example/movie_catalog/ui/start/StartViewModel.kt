package com.example.movie_catalog.ui.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.ErrorApp
import com.example.movie_catalog.entity.ImageStart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()

    private var _listImage = MutableStateFlow(listOf<ImageStart>())
    var listImage = _listImage.asStateFlow()

    init {
         getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.synchronizationDataCenterAndDB()
                dataRepository.getImageStart()
            }.fold(
                onSuccess = {_listImage.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
}