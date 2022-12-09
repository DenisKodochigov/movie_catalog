package com.example.movie_catalog.entity

import com.example.movie_catalog.App
import com.example.movie_catalog.R

data class FilmographyTab (
    val key: String? = null,
    val nameDisplay: String? = null,
){

    companion object {
        val profKey = mapOf(
            "ACTOR" to App.context.getString(R.string.ACTOR),
            "HERSELF" to App.context.getString(R.string.HERSELF),
            "HIMSELF" to App.context.getString(R.string.HIMSELF),
            "PRODUCER" to App.context.getString(R.string.PRODUCER),
            "DIRECTOR" to App.context.getString(R.string.DIRECTOR),
            "WRITER" to App.context.getString(R.string.WRITER),
            "OPERATOR" to App.context.getString(R.string.OPERATOR),
            "DESIGN" to App.context.getString(R.string.DESIGN),
            "EDITOR" to App.context.getString(R.string.EDITOR),
            "COMPOSER" to App.context.getString(R.string.COMPOSER),
        )
    }
}