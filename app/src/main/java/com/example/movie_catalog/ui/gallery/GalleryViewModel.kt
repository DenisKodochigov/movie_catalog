package com.example.movie_catalog.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Gallery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilm: Film? = null

    private var _gallery = MutableStateFlow( Gallery())
    var galleryFlow = _gallery.asStateFlow()

    init {
        takeFilm()
        localFilm?.let { getImages(it)}
    }

    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            if (localFilm != null) _gallery.value = dataRepository.getGallery(film)
        }
    }
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    fun putFilm(){
        localFilm?.let { dataRepository.putFilm(it)}
    }
}