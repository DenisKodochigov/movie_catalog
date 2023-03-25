package com.example.movie_catalog.ui.images

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
class ImagesViewModel @Inject constructor(private var dataRepository: DataRepository): ViewModel() {

    //The movie that is displayed on the page
    private var localFilm: Film? = null
    // Images data chanel
    private var _listImage = MutableStateFlow(Gallery())
    var listImage = _listImage.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilm()
        localFilm?.let{ getImages(it) }
    }
    //Get a list of images to display
    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            _listImage.value = dataRepository.getGallery(film)
        }
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
}