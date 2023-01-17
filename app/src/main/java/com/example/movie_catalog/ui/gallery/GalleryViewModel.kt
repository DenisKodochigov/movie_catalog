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
    //The movie that is displayed on the page
    private var localFilm: Film? = null
    //Photo output stream
    private var _gallery = MutableStateFlow( Gallery())
    var galleryFlow = _gallery.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilm()
        localFilm?.let { getImages(it)}
    }
    //Get a list of images to display
    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            if (localFilm != null) _gallery.value = dataRepository.getGallery(film)
        }
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
    //Save the film.object for the next fragment
    fun putFilm(){
        localFilm?.let { dataRepository.putFilm(it)}
    }
}