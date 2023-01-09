package com.example.movie_catalog.data.api

import com.example.movie_catalog.data.api.film_info.FilmImageDTO
import com.example.movie_catalog.data.api.film_info.FilmInfoDTO
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.data.api.film_info.SimilarDTO
import com.example.movie_catalog.data.api.home.filter.FilterDTO
import com.example.movie_catalog.data.api.home.getKit.ListGenresDTO
import com.example.movie_catalog.data.api.home.premieres.PremieresDTO
import com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
import com.example.movie_catalog.data.api.home.top.TopFilmsDTO
import com.example.movie_catalog.data.api.person.PersonInfoDTO
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val SERVER_API = "https://kinopoiskapiunofficial.tech"

interface KinopoiskAPI {

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(@Query("year") year:Int, @Query("month") month: String): PremieresDTO

    //TOP_100_POPULAR_FILMS  TOP_250_BEST_FILMS
    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top")
    suspend fun getTop(@Query("page") page: Int, @Query("type") type: String): TopFilmsDTO

    //Order = RATING сортировка
    // type = FILM, TV_SHOW, TV_SERIES, MINI_SERIES, ALL
    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films?order=RATING&type=TV_SERIES&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getFilters(@Query("page") page: Int, @Query("order") order: String,
                           @Query("type") type: String, @Query("ratingFrom") ratingFrom: Int,
                           @Query("ratingTo") ratingTo: Int, @Query("yearFrom") yearFrom: Int,
                           @Query("yearTo") yearTo: Int): FilterDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun getFilters(@Query("page") page: Int, @Query("order") order: String,
                    @Query("type") type: String, @Query("ratingFrom") ratingFrom: Int,
                    @Query("ratingTo") ratingTo: Int, @Query("yearFrom") yearFrom: Int,
                    @Query("yearTo") yearTo: Int, @Query("keyword") keyword: String,
                    @Query("countries") countries: Int?, @Query("genres") genres: Int?): FilterDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun getGenres(): ListGenresDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(@Path("id") id:Int): FilmInfoDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSeasons(@Path("id") id:Int): SeasonsDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun getPersons(@Query("filmId") id:Int): List<PersonDTO>

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun getPersonInfo(@Path("id") id:Int): PersonInfoDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images?")
    suspend fun getGallery(@Path("id") id:Int, @Query("type") type: String,
                           @Query("page") page: Int): FilmImageDTO

    @Headers("Accept: application/json", "Content-type: application/json", "X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilar(@Path("id") id:Int): SimilarDTO

    companion object{
        private const val api_key = "f8b0f389-e491-48d0-8794-240a6d0bc635"
    }
}

// 20c3f30c-7ba7-4417-9c72-4975ac6091c6
// f8b0f389-e491-48d0-8794-240a6d0bc635
// 130f6e6d-9e90-4c52-8cf5-8c4cda072fa8

val retrofitApi: KinopoiskAPI by lazy {
    Retrofit
        .Builder()
        .baseUrl(SERVER_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(KinopoiskAPI::class.java)
}