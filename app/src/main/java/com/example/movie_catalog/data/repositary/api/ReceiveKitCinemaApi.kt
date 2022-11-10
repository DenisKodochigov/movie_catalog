package com.example.movie_catalog.data.repositary.api

import com.example.movie_catalog.data.repositary.KitCinemaDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject

class ReceiveKitCinemaApi @Inject constructor() {

    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl(SERVER_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    suspend fun getDataFormApiPaging(page: Int): KitCinemaDTO {
        val dataApi: GetAPI = retrofit.create(GetAPI::class.java)
        return dataApi.getPageData("B4vnKOgACgg4BWBUvGajayMiCXEJo6mEX9um1nI6", "2022-1-1", page)
    }
}

private const val SERVER_API = "https://api.nasa.gov"

interface GetAPI {
    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPageData(
        @Query("api_key") api_key: String, @Query("earth_date") earth_date: String,
        @Query("page") page: Int
    ): KitCinemaDTO
}
