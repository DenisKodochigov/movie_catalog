package com.example.movie_catalog.data.repositary.api

import com.example.movie_catalog.entity.filminfo.FilmInfo
import com.example.movie_catalog.entity.filminfo.person.Person
import com.example.movie_catalog.data.repositary.api.home.filter.FilterFilmDTO
import com.example.movie_catalog.data.repositary.api.home.getKit.ListGenresDTO
import com.example.movie_catalog.data.repositary.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.repositary.api.home.seasons.SeasonsDTO
import com.example.movie_catalog.data.repositary.api.home.top.TopFilms
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
    suspend fun getGenres(): ListGenresDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(@Query("year") year:Int, @Query("month") month: String): PremieresDTO

    //TOP_100_POPULAR_FILM  TOP_250_BEST_FILMS
    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?page=1")
    suspend fun getTop(@Query("page") page: Int, @Query("type") type: String): TopFilms

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films?order=RATING&type=TV_SERIES&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSerials(@Query("page") page: Int): FilterFilmDTO
    //Order = RATING сортировка
    // type = FILM, TV_SHOW, TV_SERIES, MINI_SERIES, ALL

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films?order=RATING&type=FILM&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getFilters(@Query("countries") countries: Int,@Query("genres") genres: Int,
                           @Query("page") page: Int): FilterFilmDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(@Path("id") id:Int): FilmInfo

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getSeasons(@Path("id") id:Int): SeasonsDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun getActors(@Query("filmId") id:Int): List<Person>

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getGallery(@Path("id") id:Int): SeasonsDTO


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