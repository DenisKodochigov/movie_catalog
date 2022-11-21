package com.example.movie_catalog.data.repositary.api

import com.example.movie_catalog.entity.filminfo.FilmInfo
import com.example.movie_catalog.entity.filminfo.person.Person
import com.example.movie_catalog.entity.home.filter.FilterFilm
import com.example.movie_catalog.entity.home.getKit.ListGenres
import com.example.movie_catalog.entity.home.premieres.Premieres
import com.example.movie_catalog.entity.home.seasons.Seasons
import com.example.movie_catalog.entity.home.top.TopFilm
import com.example.movie_catalog.entity.home.top.TopFilms
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val SERVER_API = "https://kinopoiskapiunofficial.tech"

interface KinopoiskAPI {

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun getGenres(): ListGenres

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(@Query("year") year:Int, @Query("month") month: String): Premieres

    //TOP_100_POPULAR_FILM  TOP_250_BEST_FILMS
    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top")
    suspend fun getTop(@Query("page") page: Int, @Query("type") type: String): TopFilms

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films?order=RATING&type=TV_SERIES&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSerials(@Query("page") page: Int): FilterFilm
    //Order = RATING сортировка
    // type = FILM, TV_SHOW, TV_SERIES, MINI_SERIES, ALL

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films?order=RATING&type=FILM&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getFilters(@Query("countries") countries: Int,@Query("genres") genres: Int,
                           @Query("page") page: Int): FilterFilm

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(@Path("id") id:Int): FilmInfo

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getSeasons(@Path("id") id:Int): Seasons

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun getActors(@Query("filmId") id:Int): List<Person>

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getGallery(@Path("id") id:Int): Seasons


    companion object{
        const val pageSize = 20
        private const val api_key = "f8b0f389-e491-48d0-8794-240a6d0bc635"

    }
}

val retrofitApi: KinopoiskAPI by lazy {
    Retrofit
        .Builder()
        .baseUrl(SERVER_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(KinopoiskAPI::class.java)
}