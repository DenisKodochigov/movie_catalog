package com.example.movie_catalog.data.repositary.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_catalog.data.Data

@Database(entities = arrayOf(Data::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
