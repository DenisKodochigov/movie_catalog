package com.example.movie_catalog.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.filminfo.Gallery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var currentFilmId: Int? = null

    private var _gallery = MutableStateFlow(
        Gallery(tabs = mutableListOf(), listImage = mutableListOf())
    )
    var galleryFlow = _gallery.asStateFlow()

    init {
        takeFilmId()
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentFilmId != null) _gallery.value = dataRepository.getGallery(currentFilmId!!)
        }
    }
    fun takeFilmId() {
        val filmId = dataRepository.takeFilmId()
        if (filmId != null) currentFilmId = filmId
    }
}