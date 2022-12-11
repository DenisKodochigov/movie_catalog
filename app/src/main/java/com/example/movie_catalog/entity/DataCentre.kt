package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.film_info.SimilarDTO
import com.example.movie_catalog.data.api.home.filter.FilterDTO
import com.example.movie_catalog.data.api.home.premieres.FilmDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmsDTO
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.entity.filminfo.Kit

object DataCentre {
    var films: MutableList<Film> = mutableListOf()
    var persons: MutableList<Person> = mutableListOf()
    var galleryes = Gallery()
    var tabFilmography = FilmographyTab()


    fun addFilms(listFilm: PremieresDTO) {
        listFilm.items.forEach { filmDTO ->
            if (films.find { it.filmId == filmDTO.kinopoiskId } == null) {
                films.add(
                    Film(
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
                        kit = Kit.PREMIERES
                    )
                )
            }
        }
    }

    fun addFilms(item: TopFilmsDTO, kit:Kit) {
        item.films.forEach { topfilmDTO ->
            if (films.find { it.filmId == topfilmDTO.filmId } == null) {
                films.add(
                    Film(
                        filmId = topfilmDTO.filmId,
                        imdbId = null,
                        nameRu = topfilmDTO.nameRu,
                        nameEn = topfilmDTO.nameEn,
                        rating = topfilmDTO.rating,
                        posterUrlPreview = topfilmDTO.posterUrlPreview,
                        countries = topfilmDTO.countries,
                        genres = topfilmDTO.genres,
                        viewed = false,
                        favorite = false,
                        bookmark = false,
                        kit = kit
                    )
                )
            }
        }
        films.shuffle()
    }

    @JvmName("addFilms1")
    fun addFilms(filmPlug: List<Film>) {
        films.addAll(filmPlug)
    }

    fun addFilms(items: MutableList<FilmDTO>) {
        items.forEach { filmDTO ->
            if (films.find { it.filmId == filmDTO.kinopoiskId } == null) {
                films.add(
                    Film(
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
                        kit = Kit.PREMIERES
                    )
                )
            }
        }
    }
    fun addFilms(filterDTO: FilterDTO, kit:Kit) {
        if (filterDTO.items != null){
            filterDTO.items!!.forEach { film ->
                if (films.find { it.filmId == film.kinopoiskId } == null) {
                    films.add(
                        Film(
                            filmId = film.kinopoiskId,
                            imdbId = film.imdbId,
                            nameRu = film.nameRu,
                            nameEn = film.nameEn,
                            rating = film.ratingKinopoisk.toString(),
                            posterUrlPreview = film.posterUrlPreview,
                            countries = film.countries,
                            genres = film.genres,
                            viewed = false,
                            favorite = false,
                            bookmark = false,
                            kit = kit
                        )
                    )
                }
            }
        }
        films.shuffle()
    }

    fun setViewed(id: Int) {
        films.find { it.filmId == id }.let { if (it != null) it.viewed = !it.viewed }
    }

    fun getViewed(id: Int) = films.find { it.filmId == id }?.viewed ?: false

    fun setFavorite(id: Int) {
        films.find { it.filmId == id }.let { if (it != null) it.favorite = !it.favorite }
    }

    fun getFavorite(id: Int) = films.find { it.filmId == id }?.favorite ?: false

    fun setBookmark(id: Int) {
        films.find { it.filmId == id }.let { if (it != null) it.bookmark = !it.bookmark }
    }

    fun getBookmark(id: Int) = films.find { it.filmId == id }?.viewed ?: false

    fun addFilms(similar: SimilarDTO, idPerson:Int) {
        if (similar.items != null){
            similar.items.forEach { film ->
                if (films.find { it.filmId == film.filmId } == null) {
                    films.add(
                        Film(
                            filmId = film.filmId,
                            imdbId = null,
                            nameRu = film.nameRu,
                            nameEn = film.nameEn,
                            rating = null,
                            posterUrlPreview = film.posterUrlPreview,
                            countries = emptyList(),
                            genres = emptyList(),// retrofitApi.getFilmInfo(it.filmId!!).genres!!,
                            viewed = false,
                            favorite = false,
                            bookmark = false
                        )
                    )
                }else{
                    films.find { it.filmId == film.filmId }!!.similar = idPerson
                }
            }
        }
    }
}