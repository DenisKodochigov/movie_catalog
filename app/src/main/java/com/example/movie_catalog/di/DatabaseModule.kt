package com.example.movie_catalog.di

import android.content.Context
import androidx.room.Room
import com.example.movie_catalog.data.room.ActionDB
import com.example.movie_catalog.data.room.AppDatabase
import com.example.movie_catalog.data.room.DataDao
import com.example.movie_catalog.data.room.DataSourceDB
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    lateinit var database: AppDatabase
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        database = Room.databaseBuilder(
//            appContext, AppDatabase::class.java, "data.db").build()
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        return database
    }

    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}

