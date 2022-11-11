package com.example.movie_catalog.data.repositary.api

import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmList
import com.example.movie_catalog.entity.Listgenres
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.time.Month

private const val SERVER_API = "https://kinopoiskapiunofficial.tech"

interface KinopoiskAPI {

    @Headers("Accept: application/json", "Content-type: application/json",
                "Value: page = $pageSize", "Value: X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun getGenres(): Listgenres

    @Headers("Accept: application/json", "Content-type: application/json",
        "Value: page = $pageSize", "Value: X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(@Query("year") year:Int, @Query("month") month: String): List<Film>

    companion object{
        const val pageSize = 20
        private const val api_key = "f8b0f389-e491-48d0-8794-240a6d0bc635"
        val retrofitApi by lazy {
            Retrofit
                .Builder()
                .baseUrl(SERVER_API)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create<KinopoiskAPI>()
        }
    }
}