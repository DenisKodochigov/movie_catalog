package com.example.movie_catalog.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.data.DataRepository
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.entity.ErrorApp
import com.example.movie_catalog.entity.SearchFilter
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel()  {

    private val dataRepository = DataRepository()
    var filter = SearchFilter()
    //Data chanel for country
    private var _countries = MutableStateFlow(listOf<CountryIdDTO>())
    var countries = _countries.asStateFlow()
    //Data chanel for genres
    private var _genres = MutableStateFlow(listOf<GenreIdDTO>())
    var genres = _genres.asStateFlow()
    //Requesting data when starting a fragment
    init {
        takeFilter()
        takeCountries()
        takeGenres()
    }
    //Get current value search settings
    private fun takeFilter(){
        filter = dataRepository.takeSearchFilter()
    }
    //Get a list of countries
    private fun takeCountries(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getCountries()
            }.fold(
                onSuccess = { if (it.isNotEmpty()) _countries.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Get a list of genres
    private fun takeGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getGenres()
            }.fold(
                onSuccess = { if (it.isNotEmpty()) _genres.value = it },
                onFailure = { ErrorApp().errorApi(it.message!!) }
            )
        }
    }
    //Seva the type movie
    fun takeFilterType(typeFilm: TypeFilm){
        filter.typeFilm = typeFilm
        dataRepository.putSearchFilter(filter)
    }
    //Save the value sorting
    fun takeFilterSorting(sorting: SortingField){
        filter.sorting = sorting
        dataRepository.putSearchFilter(filter)
    }
    //Save the value filter - watched movie
    fun takeViewed(viewed: Boolean){
        filter.viewed = viewed
        dataRepository.putSearchFilter(filter)
    }
    //Save the rating range for the search filter
    fun takeRating(rating: Pair<Double,Double>){
        filter.rating = rating
        dataRepository.putSearchFilter(filter)
    }
    //Save the years range for the search filter
    fun takeYears(year: Pair<Int,Int>){
        filter.year = year
        dataRepository.putSearchFilter(filter)
    }
    //Save the country for the search filter
    fun takeCountry(country: CountryIdDTO){
        filter.country = country
        dataRepository.putSearchFilter(filter)
    }
    //Save the genre for the search filter
    fun takeGenres(genre: GenreIdDTO){
        filter.genre = genre
        dataRepository.putSearchFilter(filter)
    }
}