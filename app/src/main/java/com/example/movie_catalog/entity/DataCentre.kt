package com.example.movie_catalog.entity

import android.util.Log
import com.example.movie_catalog.data.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarDTO
import com.example.movie_catalog.data.api.home.filter.FilterDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.top.TopFilmsDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import com.example.movie_catalog.entity.enumApp.ImageGroup
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ProfKey
import com.example.movie_catalog.entity.filminfo.ImageFilm
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons

object DataCentre {
    var films = mutableListOf<Film>()
    var persons = mutableListOf<Person>()
    var imageUrl = mutableListOf<FilmImageUrlDTO>()
    var linkers = mutableListOf<Linker>()

    private var currentJobPerson: String? = null
    private var currentKit: Kit? = null
    private var currentFilm: Film? = null
    private var currentPerson: Person? = null
    private var currentKeyListFragment: String? = null

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
                    rating = topfilmDTO.rating,
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
    fun addFilms(filterDTO: FilterDTO, kit: Kit) {
        if (filterDTO.items != null) {
            filterDTO.items!!.forEach { filmDTO ->
                var film = films.find { it.filmId == filmDTO.kinopoiskId }
                if (film == null) {
                    film = Film(
                        filmId = filmDTO.kinopoiskId,
                        imdbId = filmDTO.imdbId,
                        nameRu = filmDTO.nameRu,
                        nameEn = filmDTO.nameEn,
                        rating = filmDTO.ratingKinopoisk.toString(),
                        posterUrlPreview = filmDTO.posterUrlPreview,
                        countries = filmDTO.countries,
                        genres = filmDTO.genres,
                        viewed = false,
                        favorite = false,
                        bookmark = false,
                    )
                    films.add(film)
                }
                addLinker(film, kit)
            }
        }
        films.shuffle()
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
                addLinker(film,filmBase)
            }
        }
    }
    fun addFilm(filmInfo: InfoFilmSeasons, film: Film) {
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
                    nameEn = filmPerson.nameEn, rating = filmPerson.rating,
                    professionKey = filmPerson.professionKey
                )
                films.add(film)  //Добавляем новый фильм к базе.
            }
            addLinker(film, person, filmPerson.professionKey!!)
        }
    }

    private fun addLinker(film:Film, kit: Kit){
        if (linkers.find {(it.film == film) && (it.kit == kit)} == null ) {
            linkers.add(Linker(film = film, kit = kit))
        }
    }
    private fun addLinker(film:Film, similar: Film){
        if (linkers.find {(it.film == film) && (it.similarFilm == similar)} == null ) {
            linkers.add(Linker(film = film, similarFilm = similar))
        }
    }
    private fun addLinker(film:Film, person: Person, profKey: String){
        if (linkers.find {(it.film == film) && (it.person == person)  &&
                    (it.profKey!!.name == profKey) } == null ) {
            person.professionKey?.let {
                try {
                    val itemProfKey = ProfKey.valueOf(profKey)
                    linkers.add(Linker(film = film, person = person, profKey = itemProfKey))
                } catch (e: IllegalAccessException){
                    Log.d("KDS", "Don't find ${person.professionKey}. Error $e")
                }
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
    fun putKit(id: Kit) {
        currentKit = id
    }
    fun takeKeyListFragment(): String? {
        val it = currentKeyListFragment
        currentKeyListFragment = null
        return it
    }
    fun putKeyListFragment(item: String) {
        currentKeyListFragment = item
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


//    private fun addLinker(film:Film, person: Person){
//        if (linkers.find {(it.film == film) && (it.person == person)} == null ) {
//            person.professionKey?.let {
//                try {
//                    linkers.add(Linker(film = film, person = person))
//                } catch (e: IllegalAccessException){
//                    Log.d("KDS", "Don't find ${person.professionKey}. Error $e")
//                }
//            }
//        }
//    }
//    fun takeListForListFragment(key:String): List<Linker> {
//        val listFilm = mutableListOf<Linker>()
////        listFilm.addAll(films.filter { it.filmId in listForListFragment })
////        listForListFragment = mutableListOf()
//        return listFilm
//    }
//    fun putListForListFragment(item: List<Int>) {
//        listForListFragment.clear()
//        listForListFragment.addAll(item)
//    }
//    fun addImageOld(id: Int, tab: TabImage, filmImageDTO: FilmImageDTO) {
//        val film = films.find { it.filmId == id }
//        if (film != null) {
//            filmImageDTO.items.forEach {
//                film.images.add(
//                    ImageFilm(imageUrl = it.imageUrl, previewUrl = it.previewUrl, tab = tab)
//                )
//            }
//        }
//    }
//    fun addPersonOld(personsDTO: List<PersonDTO>, id: Int) {
//
//        personsDTO.forEach { item ->
//            if (persons.find { it.personId == item.staffId } == null) {
//                val personNew = Person(
//                    personId = item.staffId,
//                    nameRu = item.nameRu,
//                    nameEn = item.nameEn,
//                    posterUrl = item.posterUrl,
//                    professionKey = item.professionKey,
//                    description = item.description,
//                    professionText = item.professionText,
//                    tabs = mutableListOf(),
//                )
//                personNew.tabs.add( FilmographyTab( key = item.professionKey,
//                    nameDisplay = FilmographyTab.profKey[item.professionKey] ))
//                personNew.tabs[0].filmsID.add(id)
//                persons.add(personNew)
//            }
//        }
//    }
//    fun addPersonInfoOld(personInfo: PersonInfoDTO) {
//
//        val tabsProfKey = mutableListOf<FilmographyTab>()
//
//        personInfo.films.forEach { filmPerson ->
//            //Check in massiv profissional key
//            val tabProfKey = tabsProfKey.find { it.key == filmPerson.professionKey }
//            if (tabProfKey != null) {
//                tabProfKey.filmsID.add(filmPerson.filmId!!)
//            } else {
//                val tab = FilmographyTab( key = filmPerson.professionKey,
//                    nameDisplay = FilmographyTab.profKey[filmPerson.professionKey])
//                tab.filmsID.add(filmPerson.filmId!!)
//                tabsProfKey.add(tab)
//            }
//            if (this.films.find { it.filmId == filmPerson.filmId } == null) {
//                this.films.add(
//                    Film(
//                        filmId = filmPerson.filmId, nameRu = filmPerson.nameRu,
//                        nameEn = filmPerson.nameEn, rating = filmPerson.rating,
//                        professionKey = filmPerson.professionKey
//                    )
//                )
//            }
//        }
//
//        var person = persons.find { it.personId == personInfo.personId }
//        if (person != null) {
//            with(person) {
//                personInfo.nameRu?.let { nameRu = personInfo.nameRu }
//                personInfo.nameEn?.let { nameEn = personInfo.nameEn }
//                personInfo.posterUrl?.let { posterUrl = personInfo.posterUrl }
//                personInfo.age?.let { personInfo.age }
//                personInfo.hasAwards?.let { hasAwards = personInfo.hasAwards }
//                personInfo.profession?.let { profession = personInfo.profession }
//                tabsProfKey.let {
//                    // Если список табов в персоне пустой, просто все коппируем
//                    if (tabs.isEmpty()){
//                        tabs.addAll(tabsProfKey)
//                    } else {
//                        // Перебираем все ProfessionalKey из сформированного ранее списка табов
//                        tabsProfKey.forEach { itTabsProfKey ->
//                            //Если в табе персоны нет ProfessionalKey, то копируем данную табу
//                            if (tabs.find { it.key == itTabsProfKey.key } == null){
//                                tabs.add(itTabsProfKey)
//                            }else{
//                                //Если есть обновляем спиок фильмов
//                                tabs.forEach { itTabs ->
//                                    itTabsProfKey.filmsID.forEach { itTabsProfKeyfilmsID ->
//                                        if ( itTabs.filmsID.find { it == itTabsProfKeyfilmsID } == null){
//                                            itTabs.filmsID.add(itTabsProfKeyfilmsID)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            person = Person(
//                personId = personInfo.personId,
//                nameRu = personInfo.nameRu,
//                nameEn = personInfo.nameEn,
//                posterUrl = personInfo.posterUrl,
//                age = personInfo.age,
//                hasAwards = personInfo.hasAwards,
//                profession = personInfo.profession,
//            )
//            person.tabs.addAll(tabsProfKey)
//            persons.add(person)
//        }
//    }

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


    //
//    fun takeFilmId(): Int? {
//        val it = currentFilmId
//        currentFilmId = null
//        return it
//    }
//
//    fun putFilmId(id: Int) {
//        currentFilmId = id
//    }
}