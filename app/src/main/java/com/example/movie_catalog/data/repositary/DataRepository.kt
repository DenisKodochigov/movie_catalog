package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.data.repositary.api.DataSourceAPI
import com.example.movie_catalog.entity.filminfo.Gallery
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private val dataSource = DataSourceAPI()

    suspend fun getPremieres()= dataSource.getPremieres()

    suspend fun getTop(page:Int, type: String)= dataSource.getTop(page, type)

    suspend fun getFilters(page:Int, genre:Int, country:Int)= dataSource.getFilters(page, genre, country)

    suspend fun getSerials(page:Int)= dataSource.getSerials(page)

    suspend fun getGenres() = dataSource.getGenres()

    suspend fun getActors(id: Int) = dataSource.getActors(id)

    suspend fun getInfoSeasson(id: Int) = dataSource.getInfoSeasson(id)

    suspend fun getGallery(id: Int) = dataSource.getGallery(id, "")

    suspend fun getSimilar(id: Int) = dataSource.getSimilar(id)

    suspend fun getGalleryFull(id: Int): Gallery {
        val gallery = Gallery()
        gallery.tabs.forEach {
            it.imagesUrl = dataSource.getGallery(id,it.nameTab.toString())
        }
        return gallery
    }
}