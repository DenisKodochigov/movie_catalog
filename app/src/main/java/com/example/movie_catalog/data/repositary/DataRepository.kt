package com.example.movie_catalog.data.repositary

import com.example.movie_catalog.App
import com.example.movie_catalog.data.repositary.api.DataSourceAPI
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.gallery.ListImages
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
        gallery.tabs.forEach { tab ->
            var page = 1
            val items = mutableListOf<FilmImageUrlDTO>()
            val filmImageDTO = dataSource.getGallery(id,tab.nameTab.toString(), page)
            items.addAll(filmImageDTO.items)
            while (filmImageDTO.totalPages!! >= page){
                page ++
                items.addAll(dataSource.getGallery(id,tab.nameTab.toString(), page).items)
            }
            tab.imagesUrl = filmImageDTO
            tab.imagesUrl!!.items = items.toList()
        }
        return gallery
    }

    suspend fun getListImage():ListImages{
        val listImages = ListImages()
        val image = App.galleryApp!!.tabs[App.imagePositionApp.positionTab!!]
                            .imagesUrl!!.items[App.imagePositionApp.positionList!!].imageUrl

        App.galleryApp!!.tabs.forEach { tab->
            tab.imagesUrl!!.items.forEach {
                listImages.images.add(it.imageUrl!!)
            }
        }
        listImages.position = listImages.images.indices.find{ listImages.images[it] == image}

        return listImages
    }
}