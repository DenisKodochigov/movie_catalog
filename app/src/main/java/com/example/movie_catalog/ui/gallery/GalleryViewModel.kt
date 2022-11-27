package com.example.movie_catalog.ui.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.App
import com.example.movie_catalog.data.repositary.DataRepository
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

    private var _gallery = MutableStateFlow(Gallery())
    var galleryFlow = _gallery.asStateFlow()

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getGalleryFull(App.filmApp.filmId!!)
            }.fold(
                onSuccess = {_gallery.value = it },
                onFailure = { Log.d("KDS",it.message ?: "")}
            )
        }
    }
}