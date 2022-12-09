package com.example.movie_catalog.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
