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

    suspend fun getPersons(id: Int) = dataSource.getPersons(id)

    suspend fun getPersonInfo(id: Int) = dataSource.getPersonInfo(id)

    suspend fun getInfoFilmSeason(id: Int) = dataSource.getInfoFilmSeason(id)

    suspend fun getSimilar(id: Int) = dataSource.getSimilar(id)

    suspend fun getGallery(id: Int): Gallery {
        val gallery = Gallery()
        gallery.tabs.forEach {
            it.imagesUrl = dataSource.getGallery(id,it.nameTab.toString())
        }
        return gallery
    }
}