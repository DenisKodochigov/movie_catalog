package com.example.movie_catalog.ui.viewer_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewerImageViewModel @Inject constructor(): ViewModel() {

    private var _listImage = MutableStateFlow<List<FilmImageUrlDTO>>(mutableListOf())
    var listImage = _listImage.asStateFlow()

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _listImage.value = App.galleryApp!!.listImageUrl.toList()
        }
    }
}