package com.example.movie_catalog.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie_catalog.R
import com.example.movie_catalog.data.room.AppDatabase
import com.example.movie_catalog.data.room.DataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import com.example.movie_catalog.data.room.tables.CollectionDB

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    lateinit var database: AppDatabase
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        database = Room.databaseBuilder( appContext, AppDatabase::class.java, "data.db")
//            .build()
        val nameCollection1 = appContext.getString(R.string.want_look)
        val nameCollection2 = appContext.getString(R.string.favourite)
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .addCallback( object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        database.dataDao().insert(CollectionDB(name = nameCollection1))
                        database.dataDao().insert(CollectionDB(name = nameCollection2))
                    }
                }
            })
            .build()

        return database
    }

    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}

