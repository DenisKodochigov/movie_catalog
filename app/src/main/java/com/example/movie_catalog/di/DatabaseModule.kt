package com.example.movie_catalog.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movie_catalog.R
import com.example.movie_catalog.data.room.AppDatabase
import com.example.movie_catalog.data.room.DataDao
import com.example.movie_catalog.entity.Collection
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

        val nameCollection1 = appContext.getString(R.string.favourite_kit)
        val imageCollection1 = R.drawable.icon_favorite
        val nameCollection2 = appContext.getString(R.string.bookmark_kit)
        val imageCollection2 = R.drawable.icon_bookmark
//        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
//            .addCallback( object: RoomDatabase.Callback(){
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                    ioThread {
//                        database.dataDao().insert(CollectionDB(
//                            collection = Collection( name = nameCollection1, image = imageCollection1)))
//                        database.dataDao().insert(CollectionDB(
//                            collection = Collection( name = nameCollection2, image = imageCollection2)))
//                    }
//                }
//            })
//            .build()
            database = Room.databaseBuilder( appContext, AppDatabase::class.java, "data.db")
                .addCallback( object: RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            database.dataDao().insert(CollectionDB(
                                collection = Collection( name = nameCollection1, image = imageCollection1)))
                            database.dataDao().insert(CollectionDB(
                                collection = Collection( name = nameCollection2, image = imageCollection2)))
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

