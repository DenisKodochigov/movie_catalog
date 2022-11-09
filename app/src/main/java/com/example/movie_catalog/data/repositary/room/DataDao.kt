package com.example.movie_catalog.data.repositary.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movie_catalog.data.Data

/* Data access object to query the database. */
@Dao
interface DataDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun getAll(): List<Data>

    @Insert
    fun insertAll(vararg data: Data)

    @Query("DELETE FROM logs")
    fun nukeTable()

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun selectAllLogsCursor(): Cursor

    @Query("SELECT * FROM logs WHERE id = :id")
    fun selectLogById(id: Long): Cursor?
}
