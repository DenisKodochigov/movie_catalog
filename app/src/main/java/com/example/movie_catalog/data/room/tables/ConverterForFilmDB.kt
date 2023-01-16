package com.example.movie_catalog.data.room.tables

import androidx.room.TypeConverter
import com.example.movie_catalog.data.api.home.seasons.EpisodeDTO
import com.example.movie_catalog.data.api.home.seasons.SeasonDTO
import com.example.movie_catalog.entity.Country
import com.example.movie_catalog.entity.Genre
import com.example.movie_catalog.entity.filminfo.ImageFilm
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
/*
Converter class for adding the FILE class to the database table: FilmDB
*/
class ConverterForFilmDB {
    // Converter class List<Country> to string and back
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val typeListCountry: ParameterizedType = Types.newParameterizedType(List::class.java, Country::class.java)
    private val countryAdapter: JsonAdapter<List<Country>> = moshi.adapter(typeListCountry)
    @TypeConverter
    fun countryToJSON(source: List<Country>?): String = countryAdapter.toJson(source)
    @TypeConverter
    fun countryFromJSON(sourceStr: String) = countryAdapter.fromJson(sourceStr)
    // Converter class List<Genre> to string and back
    private val typeListGenre: ParameterizedType = Types.newParameterizedType(List::class.java, Genre::class.java)
    private val genreAdapter: JsonAdapter<List<Genre>> = moshi.adapter(typeListGenre)
    @TypeConverter
    fun genreToJSON(source: List<Genre>?): String = genreAdapter.toJson(source)
    @TypeConverter
    fun genreFromJSON(sourceStr: String) = genreAdapter.fromJson(sourceStr)
    // Converter class List<ImageFilm> to string and back
    private val typeListImage: ParameterizedType = Types.newParameterizedType(List::class.java, ImageFilm::class.java)
    private val imageAdapter: JsonAdapter<List<ImageFilm>> = moshi.adapter(typeListImage)
    @TypeConverter
    fun imageToJSON(source: List<ImageFilm>?): String = imageAdapter.toJson(source)
    @TypeConverter
    fun imageFromJSON(sourceStr: String) = imageAdapter.fromJson(sourceStr)

    // Converter class List<SeasonDTO> to string and back
    private val typeListSeason: ParameterizedType = Types.newParameterizedType(List::class.java, SeasonDTO::class.java)
    private val seasonAdapter: JsonAdapter<List<SeasonDTO>> = moshi.adapter(typeListSeason)
    @TypeConverter
    fun seasonToJSON(source: List<SeasonDTO>?): String = seasonAdapter.toJson(source)
    @TypeConverter
    fun seasonFromJSON(sourceStr: String) = seasonAdapter.fromJson(sourceStr)

    //Converter class List<EpisodeDTO> to string and back
    private val typeListEpisode: ParameterizedType = Types.newParameterizedType(List::class.java, EpisodeDTO::class.java)
    private val episodeAdapter: JsonAdapter<List<EpisodeDTO>> = moshi.adapter(typeListEpisode)
    @TypeConverter
    fun episodeToJSON(source: List<EpisodeDTO>?): String = episodeAdapter.toJson(source)
    @TypeConverter
    fun episodeFromJSON(sourceStr: String) = episodeAdapter.fromJson(sourceStr)
}