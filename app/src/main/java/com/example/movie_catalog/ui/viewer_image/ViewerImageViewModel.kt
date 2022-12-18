package com.example.movie_catalog.ui.viewer_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.ImageFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerImageViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    private var localFilm: Film? = null
    private var _listImage = MutableStateFlow<List<ImageFilm>>(mutableListOf())
    var listImage = _listImage.asStateFlow()

    init {
        takeFilm()
        localFilm?.let{ getImages(it) }
    }

    private fun getImages(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            _listImage.value = dataRepository.getImages(film)
        }
    }
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
}