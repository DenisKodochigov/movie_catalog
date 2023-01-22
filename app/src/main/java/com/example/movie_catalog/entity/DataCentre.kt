package com.example.movie_catalog.entity

import android.util.Log
import com.example.movie_catalog.data.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarDTO
import com.example.movie_catalog.data.api.home.filter.FilterDTO
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.data.api.home.getKit.ListGenresDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmsDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.enumApp.*
import com.example.movie_catalog.entity.filminfo.ImageFilm
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import java.util.HashMap

object DataCentre {
    var films = mutableListOf<Film>()
    private var persons = mutableListOf<Person>()
    var linkers = mutableListOf<Linker>()
    var countries = mutableListOf<CountryIdDTO>()
    var genres = mutableListOf<GenreIdDTO>()
    val headers = HashMap<String, String>()

    private var currentJobPerson: String? = null
    private var currentKit: Kit? = null
    private var currentFilm: Film? = null
    private var currentPerson: Person? = null
    private var searchFilter = SearchFilter(
        country = CountryIdDTO(id = 1, country = "США"),
        genre = GenreIdDTO(id = 11, genre = "боевик"),
        typeFilm = TypeFilm.FILM,
        year = Pair(1999,2020),
        rating = Pair(1.0, 10.0),
        viewed = false,
        sorting = SortingField.YEAR
    )

    fun setApiKey(){
        when(headers["X-API-KEY"]){
            "" -> headers["X-API-KEY"] = "20c3f30c-7ba7-4417-9c72-4975ac6091c6"
            "20c3f30c-7ba7-4417-9c72-4975ac6091c6" -> headers["X-API-KEY"] = "f8b0f389-e491-48d0-8794-240a6d0bc635"
            "f8b0f389-e491-48d0-8794-240a6d0bc635" -> headers["X-API-KEY"] = "130f6e6d-9e90-4c52-8cf5-8c4cda072fa8"
            "130f6e6d-9e90-4c52-8cf5-8c4cda072fa8" -> headers["X-API-KEY"] = "20c3f30c-7ba7-4417-9c72-4975ac6091c6"
            else -> headers["X-API-KEY"] = "20c3f30c-7ba7-4417-9c72-4975ac6091c6"
        }
    }

    fun addFilms(listFilm: List<Film>) {
        listFilm.forEach { item ->
            films.add(item)
            addLinker(item)
        }
    }

    fun addFilms(listFilm: PremieresDTO) {
        listFilm.items.forEach { filmDTO ->
            var film = films.find { it.filmId == filmDTO.kinopoiskId }
            if (film == null) {
                film = Convertor().fromFilmDTOtoFilm(filmDTO)
                films.add(film)
            }
            addLinker(film, Kit.PREMIERES)
        }
    }

    fun addFilms(topFilmsDTO: TopFilmsDTO, kit: Kit) {
        topFilmsDTO.films.forEach { topfilmDTO ->
            var film = films.find { it.filmId == topfilmDTO.filmId }
            if (film == null) {
                film = Convertor().fromTopFilmDTOtoFilm(topfilmDTO)
                films.add(film)
            }
            addLinker(film, kit)
        }
        films.shuffle()
    }

    fun addFilms(filterDTO: FilterDTO, kit: Kit): List<Linker> {
        val listLinkers = mutableListOf<Linker>()
        if (filterDTO.items != null) {
            filterDTO.items!!.forEach { filmDTO ->
                var film = films.find { it.filmId == filmDTO.kinopoiskId }
                if (film == null) {
                    film = Convertor().fromFilterFilmDTOtoFilm(filmDTO)
                    films.add(film)
                }
                listLinkers.add(Linker(film, null, null, null, kit))
                addLinker(film, kit)
            }
        }
        return listLinkers
    }

    fun addFilms(similar: SimilarDTO, film: Film) {
        if (similar.items != null) {
            similar.items.forEach { filmSimilar ->
                //Check in list similar current film
                var filmBase = films.find { it.filmId == filmSimilar.filmId }
                if (filmBase == null) {
                    filmBase = Convertor().fromSimilarFilmDTOtoFilm(filmSimilar)
                    films.add(filmBase)
                }
                addLinker(film, filmBase)
            }
        }
    }

    fun addFilm(filmInfo: InfoFilmSeasons, film: Film) {

        with(film) {
            filmInfo.infoFilm?.imdbId?.let { imdbId = it }
            filmInfo.infoFilm?.nameRu?.let { nameRu = it }
            filmInfo.infoFilm?.nameEn?.let { nameEn = it }
            filmInfo.infoFilm?.nameOriginal?.let { nameOriginal = it }
            filmInfo.infoFilm?.description?.let { description = it }
            filmInfo.infoFilm?.shortDescription?.let { shortDescription = it }
            filmInfo.infoFilm?.countries?.let { countries = it }
            filmInfo.infoFilm?.genres?.let { genres = it }
            filmInfo.infoFilm?.posterUrl?.let { posterUrl = it }
            filmInfo.infoFilm?.logoUrl?.let { logoUrl = it }
            filmInfo.infoFilm?.posterUrlPreview?.let { posterUrlPreview = it }
            filmInfo.infoFilm?.startYear?.let { startYear = it.toInt() }
            filmInfo.infoFilm?.year?.let { year = it }
            filmInfo.infoFilm?.ratingKinopoisk?.let { rating = it }
            filmInfo.infoFilm?.ratingImdb?.let { ratingImdb = it }
            filmInfo.infoFilm?.ratingAwait?.let { ratingAwait = it }
            filmInfo.infoFilm?.ratingGoodReview?.let { ratingGoodReview = it }
            filmInfo.infoSeasons?.total?.let { totalSeasons = it }
            filmInfo.infoSeasons?.items?.let { listSeasons = it }
        }
    }

    fun addPerson(personsDTO: List<PersonDTO>, film: Film) {
        personsDTO.forEach { item ->
            if (persons.find { it.personId == item.staffId } == null) {
                val personNew = Convertor().fromPersonDTOtoPerson(item)
                persons.add(personNew)
                addLinker(film, personNew, personNew.professionKey!!)
            }
        }
    }

    fun addImage(film: Film, tab: ImageGroup, filmImageDTO: FilmImageDTO) {
        filmImageDTO.items.forEach {
            film.images.add(
                ImageFilm(imageUrl = it.imageUrl, previewUrl = it.previewUrl, imageGroup = tab)
            )
        }
    }

    fun addPersonInfo(personInfo: PersonInfoDTO) {
        //Проверяем наличие персоны в базе. Если нет создаем ее, если есть обновляем.
        var person: Person? = persons.find { it.personId == personInfo.personId }
        if (person != null) {
            with(person) {
                personInfo.nameRu?.let { nameRu = personInfo.nameRu }
                personInfo.nameEn?.let { nameEn = personInfo.nameEn }
                personInfo.posterUrl?.let { posterUrl = personInfo.posterUrl }
                personInfo.age?.let { personInfo.age }
                personInfo.hasAwards?.let { hasAwards = personInfo.hasAwards }
                personInfo.profession?.let { profession = personInfo.profession }
                personInfo.profession?.let { profession = personInfo.profession }
            }
        } else {
            person = Convertor().fromPersonInfoDTOtoPerson(personInfo)
            persons.add(person)//   .add(person)
        }
        //Перебираем входные данные по фильмам относящиеся к персоне
        personInfo.films.forEach { filmPerson ->
            //Проверяем есть ли фильм в базе с указанным ID
            var film = films.find { it.filmId == filmPerson.filmId }
            if (film == null) { //Если нет, то создаем новый фильм
                film = Film(
                    filmId = filmPerson.filmId,
                    nameRu = filmPerson.nameRu,
                    nameEn = filmPerson.nameEn,
                    rating = filmPerson.rating?.toDouble(),
                    professionKey = filmPerson.professionKey
                )
                films.add(film)  //Добавляем новый фильм к базе.
            }
            addLinker(film, person, filmPerson.professionKey!!)
        }
    }

    private fun addLinker(film: Film) {
        if (linkers.find { (it.film == film) } == null) {
            linkers.add(Linker(film = film))
        }
    }

    private fun addLinker(film: Film, kit: Kit) {
        if (linkers.find { (it.film == film) && (it.kit == kit) } == null) {
            linkers.add(Linker(film = film, kit = kit))
        }
    }

    private fun addLinker(film: Film, similar: Film) {
        if (linkers.find { (it.film == film) && (it.similarFilm == similar) } == null) {
            linkers.add(Linker(film = film, similarFilm = similar))
        }
    }

    private fun addLinker(film: Film, person: Person, profKey: String) {
        if (linkers.find {
                (it.film == film) && (it.person == person) &&
                        (it.profKey!!.name == profKey)
            } == null) {
            person.professionKey?.let {
                try {
                    val itemProfKey = ProfKey.valueOf(profKey)
                    linkers.add(Linker(film = film, person = person, profKey = itemProfKey))
                } catch (e: IllegalAccessException) {
                    Log.d("KDS", "Don't find ${person.professionKey}. Error $e")
                }
            }
        }
    }

    fun clearViewedFilms(){
        films.forEach {
            it.viewed = false
        }
    }

    fun addCountryGenres(listDTO: ListGenresDTO){
        listDTO.genres?.forEach { dto ->
            if (genres.find { it.id == dto.id } == null) {
                genres.add(GenreIdDTO(dto.id, dto.genre))
            }
        }
        listDTO.countries?.forEach {dto ->
            if (countries.find { it.id == dto.id } == null) {
                countries.add(CountryIdDTO(dto.id, dto.country))
            }
        }
    }

    fun takeFilm(): Film? {
        val it = currentFilm
        currentFilm = null
        return it
    }

    fun putFilm(film: Film) {
        currentFilm = film
    }

    fun takePerson(): Person? {
        val it = currentPerson
        currentPerson = null
        return it
    }

    fun putPerson(person: Person) {
        currentPerson = person
    }

    fun takeKit(): Kit? {
        val it = currentKit
        currentKit = null
        return it
    }

    fun putKit(kit: Kit) {
        currentKit = kit
    }

    fun putSearchFilter(it: SearchFilter){
        searchFilter.typeFilm = it.typeFilm
        searchFilter.country = it.country
        searchFilter.genre = it.genre
        searchFilter.year = it.year
        searchFilter.rating = it.rating
        searchFilter.viewed = it.viewed
        searchFilter.sorting = it.sorting
        searchFilter.keyWord = it.keyWord
    }

    fun takeSearchFilter(): SearchFilter {
        return SearchFilter(
            typeFilm = searchFilter.typeFilm,
            country = searchFilter.country,
            genre = searchFilter.genre,
            year = searchFilter.year,
            rating = searchFilter.rating,
            viewed = searchFilter.viewed,
            sorting = searchFilter.sorting,
            keyWord = searchFilter.keyWord
        )
    }
    //###############################################################################################

    fun takeJobPerson(): String? {
        val it = currentJobPerson
        currentJobPerson = null
        return it
    }

    fun putJobPerson(id: String) {
        currentJobPerson = id
    }
}

