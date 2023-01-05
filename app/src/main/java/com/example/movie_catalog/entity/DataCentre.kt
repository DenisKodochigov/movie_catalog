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

object DataCentre {
    var films = mutableListOf<Film>()
    private var persons = mutableListOf<Person>()
    var linkers = mutableListOf<Linker>()
    var countries = mutableListOf<CountryIdDTO>()
    var genres = mutableListOf<GenreIdDTO>()

    private var currentJobPerson: String? = null
    private var currentKit: Kit? = null
    private var currentFilm: Film? = null
    private var currentPerson: Person? = null
    private var searchFilter = SearchFilter(
        country = CountryIdDTO(id = 1, country = "США"),
        genre = GenreIdDTO(id = 11, genre = "боевик"),
        typeFilm = TypeFilm.FILM,
        year = Pair(1999,2020),
        rating = Pair(3.0, 10.0),
        viewed = false,
        sorting = SortingField.YEAR
    )

    fun addFilms(listFilm: PremieresDTO) {
        listFilm.items.forEach { filmDTO ->
            var film = films.find { it.filmId == filmDTO.kinopoiskId }
            if (film == null) {
                film = Film(
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
                films.add(film)
            }
            addLinker(film, Kit.PREMIERES)
        }
    }

    fun addFilms(item: TopFilmsDTO, kit: Kit) {
        item.films.forEach { topfilmDTO ->
            var film = films.find { it.filmId == topfilmDTO.filmId }
            if (film == null) {
                film = Film(
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
                    film = Film(
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
                        posterUrl = filmDTO.posterUrl
                    )
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
                    //If don't, add similar film to DataCentre.films
                    filmBase = Film(
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
                    films.add(filmBase)
                }
                addLinker(film, filmBase)
            }
        }
    }

    fun addFilm(filmInfo: InfoFilmSeasons, film: Film) {

        with(film) {
            imdbId = filmInfo.infoFilm!!.imdbId
            nameRu = filmInfo.infoFilm!!.nameRu.orEmpty()
            nameEn = filmInfo.infoFilm!!.nameEn.orEmpty()
            rating = filmInfo.infoFilm!!.ratingKinopoisk
            posterUrlPreview = filmInfo.infoFilm!!.posterUrlPreview
            countries = filmInfo.infoFilm!!.countries.orEmpty()
            genres = filmInfo.infoFilm!!.genres.orEmpty()
            startYear = filmInfo.infoFilm!!.startYear.orEmpty().toInt()
            posterUrl = filmInfo.infoFilm!!.posterUrl.orEmpty()
            logoUrl = filmInfo.infoFilm!!.logoUrl.orEmpty()
            nameOriginal = filmInfo.infoFilm!!.nameOriginal.orEmpty()
            ratingImdb = filmInfo.infoFilm!!.ratingImdb
            ratingAwait = filmInfo.infoFilm!!.ratingAwait
            ratingGoodReview = filmInfo.infoFilm!!.ratingGoodReview
            totalSeasons = filmInfo.infoSeasons?.total
            listSeasons = filmInfo.infoSeasons?.items
            year = filmInfo.infoFilm!!.year
            description = filmInfo.infoFilm!!.description.orEmpty()
            shortDescription = filmInfo.infoFilm!!.shortDescription.orEmpty()
        }
    }

    fun addPerson(personsDTO: List<PersonDTO>, film: Film) {
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
            person = Person(
                personId = personInfo.personId,
                nameRu = personInfo.nameRu,
                nameEn = personInfo.nameEn,
                posterUrl = personInfo.posterUrl,
                age = personInfo.age,
                hasAwards = personInfo.hasAwards,
                profession = personInfo.profession,
            )
            persons.add(person)//   .add(person)
        }
        //Перебираем входные данные по фильмам относящиеся к персоне
        personInfo.films.forEach { filmPerson ->
            //Проверяем есть ли фильм в базе с указанным ID
            var film = films.find { it.filmId == filmPerson.filmId }
            if (film == null) { //Если нет, то создаем новый фильм
                film = Film(
                    filmId = filmPerson.filmId, nameRu = filmPerson.nameRu,
                    nameEn = filmPerson.nameEn, rating = filmPerson.rating?.toDouble(),
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
