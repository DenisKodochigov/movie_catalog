package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarItemDTO
import com.example.movie_catalog.data.api.home.filter.FilterFilmDTO
import com.example.movie_catalog.data.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.api.home.top.TopFilmDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.data.room.tables.FilmDB

class Convertor {
//    fun fromCollectionDBtoString(source: List<CollectionDB>): List<String>{
//        val list = mutableListOf<String>()
//        source.forEach {
//            list.add(it.collection?.name ?: "")
//        }
//        return list
//    }
    fun fromCollectionDBtoCollection(source: List<CollectionDB>): List<Collection>{
        val list = mutableListOf<Collection>()
        source.forEach {
            list.add(Collection(
                name = it.collection?.name ?: "",
                count = it.collection?.count ?: 0,
                image =  it.collection?.image ?: 0,
                included =  it.collection?.included ?: false)
            )
        }
        return list
    }
    fun fromFilmDTOtoFilm(filmDTO: FilmDTO):Film{
        return Film(
            filmId = filmDTO.kinopoiskId,
            imdbId = null,
            nameRu = filmDTO.nameRu,
            nameEn = filmDTO.nameEn,
            rating = null,
            posterUrlPreview = filmDTO.posterUrlPreview,
            countries = filmDTO.countries,
            genres = filmDTO.genres,
            viewed = false,
            favorite = false,
            bookmark = false,
        )
    }
//    fun fromListFilmDTOtoFilm(listfilmDTO: List<FilmDTO>):List<Film>{
//        val listFilm = mutableListOf<Film>()
//        listfilmDTO.forEach { filmDTO ->
//            listFilm.add(fromFilmDTOtoFilm(filmDTO))
//        }
//        return listFilm
//    }
    fun fromTopFilmDTOtoFilm(topfilmDTO: TopFilmDTO):Film{
        return Film(
            filmId = topfilmDTO.filmId,
            imdbId = null,
            nameRu = topfilmDTO.nameRu,
            nameEn = topfilmDTO.nameEn,
            rating = topfilmDTO.rating?.toDouble(),
            posterUrlPreview = topfilmDTO.posterUrlPreview,
            countries = topfilmDTO.countries,
            genres = topfilmDTO.genres,
            viewed = false,
            favorite = false,
            bookmark = false,
        )
    }
    fun fromFilterFilmDTOtoFilm(filmDTO: FilterFilmDTO):Film{
        return Film(
            filmId = filmDTO.kinopoiskId,
            imdbId = filmDTO.imdbId,
            nameRu = filmDTO.nameRu,
            nameEn = filmDTO.nameEn,
            rating = filmDTO.ratingKinopoisk,
            posterUrlPreview = filmDTO.posterUrlPreview,
            countries = filmDTO.countries,
            genres = filmDTO.genres,
            viewed = false,
            favorite = false,
            bookmark = false,
            nameOriginal = filmDTO.nameOriginal,
            year = filmDTO.year,
        )
    }
    fun fromSimilarFilmDTOtoFilm(filmSimilar: SimilarItemDTO):Film{
        return Film(
            filmId = filmSimilar.filmId,
            imdbId = null,
            nameRu = filmSimilar.nameRu,
            nameEn = filmSimilar.nameEn,
            rating = null,
            posterUrlPreview = filmSimilar.posterUrlPreview,
            countries = emptyList(),
            genres = emptyList(),// retrofitApi.getFilmInfo(it.filmId!!).genres!!,
            viewed = false,
            favorite = false,
            bookmark = false,
        )
    }
    fun fromPersonDTOtoPerson(personDTO: PersonDTO):Person{
        return Person(
            personId = personDTO.staffId,
            nameRu = personDTO.nameRu,
            nameEn = personDTO.nameEn,
            posterUrl = personDTO.posterUrl,
            professionKey = personDTO.professionKey,
            description = personDTO.description,
            professionText = personDTO.professionText,
            tabs = mutableListOf(),
        )
    }
    fun fromPersonInfoDTOtoPerson(personInfo: PersonInfoDTO):Person{
        return Person(
            personId = personInfo.personId,
            nameRu = personInfo.nameRu,
            nameEn = personInfo.nameEn,
            posterUrl = personInfo.posterUrl,
            age = personInfo.age,
            hasAwards = personInfo.hasAwards,
            profession = personInfo.profession,
        )
    }
    fun fromListFilmDBtoFilm(listfilmDB: List<FilmDB>):List<Film>{
        val listFilm = mutableListOf<Film>()
        listfilmDB.forEach { filmDB ->
            filmDB.film?.let { listFilm.add(it) }
        }
        return listFilm
    }
}