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
import retrofit2.http.*

interface KinopoiskAPI {
    //Request premieres
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(@Query("year") year:Int, @Query("month") month: String,
                             @HeaderMap headers: Map<String, String>): PremieresDTO

    //Request TOP films
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/top")
    suspend fun getTop(@Query("page") page: Int, @Query("type") type: String,
                       @HeaderMap headers: Map<String, String>): TopFilmsDTO

    //Request serials
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films?order=RATING&type=TV_SERIES&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getFilters(@Query("page") page: Int, @Query("order") order: String,
                           @Query("type") type: String, @Query("ratingFrom") ratingFrom: Int,
                           @Query("ratingTo") ratingTo: Int, @Query("yearFrom") yearFrom: Int,
                           @Query("yearTo") yearTo: Int, @HeaderMap headers: Map<String, String>): FilterDTO

    //Request list movies for filter
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films")
    suspend fun getFilters(@Query("page") page: Int, @Query("order") order: String,
                    @Query("type") type: String, @Query("ratingFrom") ratingFrom: Int,
                    @Query("ratingTo") ratingTo: Int, @Query("yearFrom") yearFrom: Int,
                    @Query("yearTo") yearTo: Int, @Query("keyword") keyword: String,
                    @Query("countries") countries: Int?, @Query("genres") genres: Int?,
                    @HeaderMap headers: Map<String, String>): FilterDTO

    //Request genres and country
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/filters")
    suspend fun getGenres(@HeaderMap headers: Map<String, String>): ListGenresDTO

    //Request for additional information on a movie
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(@Path("id") id:Int, @HeaderMap headers: Map<String, String>): FilmInfoDTO

    //Request for additional information on a season
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSeasons(@Path("id") id:Int, @HeaderMap headers: Map<String, String>): SeasonsDTO

    //Request a list person
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v1/staff")
    suspend fun getPersons(@Query("filmId") id:Int, @HeaderMap headers: Map<String, String>): List<PersonDTO>

    //Request for additional information on a person
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v1/staff/{id}")
    suspend fun getPersonInfo(@Path("id") id:Int, @HeaderMap headers: Map<String, String>): PersonInfoDTO

    //Request a list of images for the movie
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/{id}/images?")
    suspend fun getGallery(@Path("id") id:Int, @Query("type") type: String, @Query("page") page: Int,
                           @HeaderMap headers: Map<String, String>): FilmImageDTO

    //Request a list of similar film for the movie
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilar(@Path("id") id: Int, @HeaderMap headers: Map<String, String>): SimilarDTO
}