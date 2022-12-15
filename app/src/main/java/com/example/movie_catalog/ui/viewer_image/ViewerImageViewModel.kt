package com.example.movie_catalog.ui.viewer_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.filminfo.Images
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerImageViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilmId: Int? = null
    private var _listImage = MutableStateFlow<List<Images>>(mutableListOf())
    var listImage = _listImage.asStateFlow()

    init {
        takeFilmId()
        localFilmId?.let{ getImages(it) }
    }

    private fun getImages(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _listImage.value = dataRepository.getImages(filmId)
        }
    }
    private fun takeFilmId() {
        val filmId = dataRepository.takeFilmId()
        if (filmId != null) localFilmId = filmId
    }
//    fun takeGallery() = dataRepository.takeGallery()
}