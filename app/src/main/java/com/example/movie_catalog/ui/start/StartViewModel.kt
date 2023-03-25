package com.example.movie_catalog.ui.start

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
class StartViewModel @Inject constructor(
    private var dataRepository: DataRepository,
    private val errorApp: ErrorApp): ViewModel() {

    //Data chanel for images
    private var _listImage = MutableStateFlow(listOf<ImageStart>())
    var listImage = _listImage.asStateFlow()
    //Requesting data when starting a fragment
    init {
         getImages()
    }
    //Request for a list of images
    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getImageStart()
            }.fold(
                onSuccess = {_listImage.value = it },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }
}