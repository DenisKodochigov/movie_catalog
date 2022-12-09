package com.example.movie_catalog.di

import android.content.Context
import androidx.room.Room
import com.example.movie_catalog.data.room.AppDatabase
import com.example.movie_catalog.data.room.DataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return Room.databaseBuilder(
//            appContext, AppDatabase::class.java, "data.db").build()
        return Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }
//inMemoryDatabaseBuilder
    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}
