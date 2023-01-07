package com.example.movie_catalog.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crossFC")
data class CrossFC(
    @PrimaryKey (autoGenerate = true) var id: Int = 0,
    val film_id: Int,
    val collection_id: Int,
    val value: Boolean = true
)
