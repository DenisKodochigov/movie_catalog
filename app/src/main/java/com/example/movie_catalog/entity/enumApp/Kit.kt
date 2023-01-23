package com.example.movie_catalog.entity.enumApp

import com.example.movie_catalog.App
import com.example.movie_catalog.R

enum class Kit(var nameKit:String = "",
               var displayText:String = "",
               var query:String = "",
               var genreID: Int = 0,
               var countryID:Int = 0) {
    PREMIERES (nameKit = App.context.getString(R.string.premieres_kit)),
    POPULAR (nameKit = App.context.getString(R.string.popular_kit), query = "TOP_100_POPULAR_FILMS"),
    SERIALS (nameKit = App.context.getString(R.string.serials_kit)),
    TOP250 (nameKit = App.context.getString(R.string.top_kit),query = "TOP_250_BEST_FILMS"),
    RANDOM1 (nameKit = App.context.getString(R.string.random1_kit)),
    RANDOM2 (nameKit = App.context.getString(R.string.random2_kit)),
    SEARCH (nameKit = App.context.getString(R.string.search_kit)),
    SIMILAR (nameKit = App.context.getString(R.string.similar_kit)),
    COLLECTION,
    PERSON
}