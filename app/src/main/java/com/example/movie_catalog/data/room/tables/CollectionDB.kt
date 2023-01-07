package com.example.movie_catalog.data.room.tables

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "collections", indices = [Index( value = ["name"], unique = true)])
data class CollectionDB (
    @PrimaryKey(autoGenerate = true) val idCollection: Int = 0,
    val name: String,
    var count: Int = 0,
    var included: Boolean = false
)