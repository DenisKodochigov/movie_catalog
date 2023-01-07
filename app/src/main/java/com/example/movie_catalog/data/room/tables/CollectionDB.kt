package com.example.movie_catalog.data.room.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "collections", indices = [Index( value = ["name"], unique = true)])
data class CollectionDB (
    @PrimaryKey(autoGenerate = true) val idCollection: Int = 0,
    @Embedded var collection: com.example.movie_catalog.entity.Collection?
)