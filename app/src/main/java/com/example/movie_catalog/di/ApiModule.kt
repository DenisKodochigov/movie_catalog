package com.example.movie_catalog.di

import com.example.movie_catalog.data.api.KinopoiskAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val serverApi = "https://kinopoiskapiunofficial.tech"
        return Retrofit
            .Builder()
            .baseUrl(serverApi)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    @Provides
    fun provideApi(retrofit: Retrofit): KinopoiskAPI {
        return retrofit.create(KinopoiskAPI::class.java)
    }
}