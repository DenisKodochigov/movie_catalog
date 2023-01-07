package com.example.movie_catalog.entity

import com.example.movie_catalog.data.room.tables.CollectionDB

class Convertor {
    fun fromCollectionDBtoString(source: List<CollectionDB>): List<String>{
        val list = mutableListOf<String>()
        source.forEach {
            list.add(it.collection?.name ?: "")
        }
        return list
    }
    fun fromCollectionDBtoCollection(source: List<CollectionDB>): List<Collection>{
        val list = mutableListOf<Collection>()
        source.forEach {
            list.add(Collection(
                name = it.collection?.name ?: "",
                count = it.collection?.count ?: 0,
                image =  it.collection?.image ?: 0,
                included =  it.collection?.included ?: false)
            )
        }
        return list
    }
}