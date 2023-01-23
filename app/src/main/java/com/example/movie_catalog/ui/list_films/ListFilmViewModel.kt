package com.example.movie_catalog.ui.list_films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.PagedSourceData
import com.example.movie_catalog.entity.ErrorApp
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.plug.Plug
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFilmViewModel @Inject constructor(): ViewModel() {

    private val dataRepository = DataRepository()
    //The kit that is displayed on the page
    var localKit: Kit? = null
    //The person object that is used on the page
    var localPerson: Person? = null
    //The film object that is used on the page
    var localFilm:Film? = null
    //Data chanel a list of linkers (movies)
    private var _listLink = MutableStateFlow(Plug.plugLinkers)
    var listLink = _listLink.asStateFlow()
    //Data chanel for premiere movies
    private var _collectionFilm = MutableStateFlow(Plug.plugLinkers)
    var collectionFilm = _collectionFilm.asStateFlow()
    //Data chanel for paging adapter
    var pagedFilms: Flow<PagingData<Linker>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PagedSourceData(localKit!!) }
    ).flow.cachedIn(viewModelScope)

    //Requesting data when starting a fragment
    init {
        takeKit()
        takeFilm()
        takePerson()

        if (localKit != null) {
            when (localKit){
                Kit.PREMIERES -> getPremieres()
                Kit.COLLECTION -> localKit?.let { getDataCollection( it.nameKit) }
                Kit.PERSON -> localPerson?.let { getListLinkerForPerson(it) }
                Kit.SIMILAR -> localFilm?.let{ getListLinkerForSimilar(it) }
                else -> {} // Other case working PagingData
            }
        }
    }
    //Request for a list of movies for a person
    private fun getListLinkerForPerson(person: Person) /*: List<Linkers>*/{
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getListLinkerForPerson(person)
            }.fold(
                onSuccess = { _listLink.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a list of similar movie to display
    private fun getListLinkerForSimilar(film: Film)/*: List<Linkers>*/ {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getListLinkerForSimilar(film)
            }.fold(
                onSuccess = { _listLink.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Request for a list of movies from the collection
    private fun getDataCollection(nameCollection: String = "")/*: List<Linkers>*/ {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getFilmsInCollectionName(nameCollection)
            }.fold(
                onSuccess = { _collectionFilm.value = it},
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
    //Request for a list of premiers
    private fun getPremieres()/*: List<Linkers>*/ {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getPremieres()
            }.fold(
                onSuccess = { _listLink.value = it},
                onFailure = { ErrorApp().errorApi(it.message!!)}
            )
        }
    }
    // Clear the viewed attribute or collection for all movies
    fun clearCollection(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                localKit?.let { kit ->
                    if (kit.nameKit == App.context.getString(R.string.viewed_kit)){
                        dataRepository.clearViewedFilm()
                    } else if (kit.nameKit == App.context.getString(R.string.viewed_kit)){
                        dataRepository.clearBookmarkFilm()
                    } else {
                        dataRepository.clearCollection(kit.nameKit)
                    }
                    //Refresh a list film of collection
                    getDataCollection(kit.nameKit)
                }
            }.fold(
                onSuccess = { },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Save the film.object for the next fragment
    fun putFilm(film: Film){
        dataRepository.putFilm(film)
    }
    //Get a saved kit object
    private fun takeKit(){
        val kit = dataRepository.takeKit()
        if (kit != null) localKit = kit
    }
    //Get a saved person object
    private fun takePerson(){
        val item = dataRepository.takePerson()
        if (item != null) localPerson = item
    }
    //Get a saved film object
    private fun takeFilm() {
        val film = dataRepository.takeFilm()
        if (film != null) localFilm = film
    }
}