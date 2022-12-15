package com.example.movie_catalog.entity

import com.example.movie_catalog.data.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarDTO
import com.example.movie_catalog.data.api.home.filter.FilterDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmsDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.filminfo.Images
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.filminfo.TabImage

object DataCentre {
    var films = mutableListOf<Film>()
    var persons = mutableListOf<Person>()
    var imageUrl = mutableListOf<FilmImageUrlDTO>()
    private var currentFilmId: Int? = null
    private var currentPersonId: Int? = null
    private var currentJobPerson: String? = null
    private var currentKit: Kit? = null
    private var listForListFragment: MutableList<Int> = mutableListOf()

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

    fun addFilms(item: TopFilmsDTO, kit: Kit) {
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

    fun addFilms(filterDTO: FilterDTO, kit: Kit) {
        if (filterDTO.items != null) {
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

    fun addFilms(similar: SimilarDTO, id: Int) {
        if (similar.items != null) {
            val currentFilm = films.find { it.filmId == id }
            similar.items.forEach { filmSimilar ->
                //Check in list similar current film
                if (currentFilm!!.similar.find { it == id } == null) {
                    //Check similar film in list DataCentre.FILMS
                    if (films.find { it.filmId == filmSimilar.filmId } == null) {
                        //If don't, add similar film to DataCentre.films
                        films.add(
                            Film(
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
                                bookmark = false
                            )
                        )
                    }
                    currentFilm.similar.add(filmSimilar.filmId!!)
                }
            }
        }
    }

    fun addFilm(filmInfo: InfoFilmSeasons, id: Int) {
        val film = films.find { it.filmId == id }
        if (film == null) {
            films.add(
                Film(
                    filmId = filmInfo.infoFilm!!.kinopoiskId,
                    imdbId = filmInfo.infoFilm!!.imdbId,
                    nameRu = filmInfo.infoFilm!!.nameRu,
                    nameEn = filmInfo.infoFilm!!.nameEn,
                    rating = filmInfo.infoFilm!!.ratingKinopoisk.toString(),
                    posterUrlPreview = filmInfo.infoFilm!!.posterUrlPreview,
                    countries = filmInfo.infoFilm!!.countries,
                    genres = filmInfo.infoFilm!!.genres!!,
                    startYear = filmInfo.infoFilm!!.startYear,
                    posterUrl = filmInfo.infoFilm!!.posterUrl,
                    logoUrl = filmInfo.infoFilm!!.logoUrl,
                    nameOriginal = filmInfo.infoFilm!!.nameOriginal,
                    ratingImdb = filmInfo.infoFilm!!.ratingImdb,
                    ratingAwait = filmInfo.infoFilm!!.ratingAwait,
                    ratingGoodReview = filmInfo.infoFilm!!.ratingGoodReview,
                    totalSeasons = filmInfo.infoSeasons?.total,
                    listSeasons = filmInfo.infoSeasons?.items,
                    year = filmInfo.infoFilm!!.year,
                    description = filmInfo.infoFilm!!.description,
                    shortDescription = filmInfo.infoFilm!!.shortDescription
                )
            )
        } else {
            if (filmInfo.infoFilm != null) {
                with(film) {
                    if (filmInfo.infoFilm!!.imdbId != null) imdbId = filmInfo.infoFilm!!.imdbId
                    if (filmInfo.infoFilm!!.nameRu != null) nameRu = filmInfo.infoFilm!!.nameRu
                    if (filmInfo.infoFilm!!.nameEn != null) nameEn = filmInfo.infoFilm!!.nameEn
                    if (filmInfo.infoFilm!!.ratingKinopoisk != null) rating =
                        filmInfo.infoFilm!!.ratingKinopoisk.toString()
                    if (filmInfo.infoFilm!!.posterUrlPreview != null) posterUrlPreview =
                        filmInfo.infoFilm!!.posterUrlPreview
                    if (filmInfo.infoFilm!!.countries != null) countries =
                        filmInfo.infoFilm!!.countries
                    if (filmInfo.infoFilm!!.genres != null) genres = filmInfo.infoFilm!!.genres!!
                    if (filmInfo.infoFilm!!.startYear != null) startYear =
                        filmInfo.infoFilm!!.startYear
                    if (filmInfo.infoFilm!!.posterUrl != null) posterUrl =
                        filmInfo.infoFilm!!.posterUrl
                    if (filmInfo.infoFilm!!.logoUrl != null) logoUrl = filmInfo.infoFilm!!.logoUrl
                    if (filmInfo.infoFilm!!.nameOriginal != null) nameOriginal =
                        filmInfo.infoFilm!!.nameOriginal
                    if (filmInfo.infoFilm!!.ratingImdb != null) ratingImdb =
                        filmInfo.infoFilm!!.ratingImdb
                    if (filmInfo.infoFilm!!.ratingAwait != null) ratingAwait =
                        filmInfo.infoFilm!!.ratingAwait
                    if (filmInfo.infoFilm!!.ratingGoodReview != null) ratingGoodReview =
                        filmInfo.infoFilm!!.ratingGoodReview
                    if (filmInfo.infoSeasons?.total != null) totalSeasons =
                        filmInfo.infoSeasons?.total
                    if (filmInfo.infoSeasons?.items != null) listSeasons =
                        filmInfo.infoSeasons?.items
                    if (filmInfo.infoFilm!!.year != null) year = filmInfo.infoFilm!!.year
                    if (filmInfo.infoFilm!!.description != null) description =
                        filmInfo.infoFilm!!.description
                    if (filmInfo.infoFilm!!.shortDescription != null) shortDescription =
                        filmInfo.infoFilm!!.shortDescription
                }
            }
        }
    }

    fun addImage(id: Int, tab: TabImage, filmImageDTO: FilmImageDTO) {
        val film = films.find { it.filmId == id }
        if (film != null) {
            filmImageDTO.items.forEach {
                film.images.add(
                    Images(imageUrl = it.imageUrl, previewUrl = it.previewUrl, tab = tab)
                )
            }
        }

    }

    fun addPerson(personsDTO: List<PersonDTO>, id: Int) {

        personsDTO.forEach { item ->
            if (persons.find { it.personId == item.staffId } == null) {
                val personNew = Person(
                    personId = item.staffId,
                    nameRu = item.nameRu,
                    nameEn = item.nameEn,
                    posterUrl = item.posterUrl,
                    professionKey = item.professionKey,
                    description = item.description,
                    professionText = item.professionText,
                    tabs = mutableListOf(),
                )
                personNew.tabs.add( FilmographyTab( key = item.professionKey,
                    nameDisplay = FilmographyTab.profKey[item.professionKey] ))
                personNew.tabs[0].filmsID.add(id)
                persons.add(personNew)
            }
        }
    }

    fun addPersonInfo(personInfo: PersonInfoDTO) {

        val tabsProfKey = mutableListOf<FilmographyTab>()

        personInfo.films.forEach { filmPerson ->
            //Check in massiv profissional key
            val tabProfKey = tabsProfKey.find { it.key == filmPerson.professionKey }
            if (tabProfKey != null) {
                tabProfKey.filmsID.add(filmPerson.filmId!!)
            } else {
                val tab = FilmographyTab( key = filmPerson.professionKey,
                    nameDisplay = FilmographyTab.profKey[filmPerson.professionKey])
                tab.filmsID.add(filmPerson.filmId!!)
                tabsProfKey.add(tab)
            }
            if (this.films.find { it.filmId == filmPerson.filmId } == null) {
                this.films.add(
                    Film(
                        filmId = filmPerson.filmId, nameRu = filmPerson.nameRu,
                        nameEn = filmPerson.nameEn, rating = filmPerson.rating,
                        professionKey = filmPerson.professionKey
                    )
                )
            }
        }

        var person = persons.find { it.personId == personInfo.personId }
        if (person != null) {
            with(person) {
                personInfo.nameRu?.let { nameRu = personInfo.nameRu }
                personInfo.nameEn?.let { nameEn = personInfo.nameEn }
                personInfo.posterUrl?.let { posterUrl = personInfo.posterUrl }
                personInfo.age?.let { personInfo.age }
                personInfo.hasAwards?.let { hasAwards = personInfo.hasAwards }
                personInfo.profession?.let { profession = personInfo.profession }
                tabsProfKey.let {
                    // Если список табов в персоне пустой, просто все коппируем
                    if (tabs.isEmpty()){
                        tabs.addAll(tabsProfKey)
                    } else {
                        // Перебираем все ProfessionalKey из сформированного ранее списка табов
                        tabsProfKey.forEach { itTabsProfKey ->
                            //Если в табе персоны нет ProfessionalKey, то копируем данную табу
                            if (tabs.find { it.key == itTabsProfKey.key } == null){
                                tabs.add(itTabsProfKey)
                            }else{
                                //Если есть обновляем спиок фильмов
                                tabs.forEach { itTabs ->
                                    itTabsProfKey.filmsID.forEach { itTabsProfKeyfilmsID ->
                                        if ( itTabs.filmsID.find { it == itTabsProfKeyfilmsID } == null){
                                            itTabs.filmsID.add(itTabsProfKeyfilmsID)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            person = Person(
                personId = personInfo.personId,
                nameRu = personInfo.nameRu,
                nameEn = personInfo.nameEn,
                posterUrl = personInfo.posterUrl,
                age = personInfo.age,
                hasAwards = personInfo.hasAwards,
                profession = personInfo.profession,
            )
            person.tabs.addAll(tabsProfKey)
            persons.add(person)
        }
    }

    fun takeFilmId(): Int? {
        val it = currentFilmId
        currentFilmId = null
        return it
    }

    fun putFilmId(id: Int) {
        currentFilmId = id
    }

    fun takePersonId(): Int? {
        val it = currentPersonId
        currentPersonId = null
        return it
    }

    fun putPersonId(id: Int) {
        currentPersonId = id
    }

    fun takeJobPerson(): String? {
        val it = currentJobPerson
        currentJobPerson = null
        return it
    }

    fun putJobPerson(id: String) {
        currentJobPerson = id
    }

    fun takeKit(): Kit? {
        val it = currentKit
        currentKit = null
        return it
    }

    fun putKit(id: Kit) {
        currentKit = id
    }

    fun takeListForListFragment(): List<Film> {
        val listFilm = mutableListOf<Film>()
        listFilm.addAll(films.filter { it.filmId in listForListFragment })
        listForListFragment = mutableListOf()
        return listFilm
    }

    fun putListForListFragment(item: List<Int>) {
        listForListFragment.clear()
        listForListFragment.addAll(item)
    }


//    fun setViewed(id: Int) {
//        films.find { it.filmId == id }.let { if (it != null) it.viewed = !it.viewed }
//    }
//
//    fun getViewed(id: Int) = films.find { it.filmId == id }?.viewed ?: false
//
//    fun setFavorite(id: Int) {
//        films.find { it.filmId == id }.let { if (it != null) it.favorite = !it.favorite }
//    }
//
//    fun getFavorite(id: Int) = films.find { it.filmId == id }?.favorite ?: false
//
//    fun setBookmark(id: Int) {
//        films.find { it.filmId == id }.let { if (it != null) it.bookmark = !it.bookmark }
//    }
//
//    fun getBookmark(id: Int) = films.find { it.filmId == id }?.viewed ?: false
}