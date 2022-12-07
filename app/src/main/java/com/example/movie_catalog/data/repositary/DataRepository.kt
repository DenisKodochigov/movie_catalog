package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.data.repositary.api.DataSourceAPI
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.filminfo.TabImage
import com.example.movie_catalog.entity.filminfo.Tab
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

        Tab.values().forEach { tab ->
            var page = 1
            val filmImageDTO = dataSource.getGallery(id,tab.toString(), page)
            if ( filmImageDTO.total!! > 0){
                while (filmImageDTO.totalPages!! >= page){
                    page ++
                    filmImageDTO.items.addAll(dataSource.getGallery(id,tab.toString(), page).items)
                }
                gallery.tabs.add(TabImage(imagesUrl = filmImageDTO, tab = tab))
                gallery.listImageUrl.addAll(filmImageDTO.items)
            }
        }
        return gallery
    }
}