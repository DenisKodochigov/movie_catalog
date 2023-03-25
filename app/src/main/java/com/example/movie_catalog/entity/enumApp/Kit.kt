package com.example.movie_catalog.entity.enumApp

import androidx.annotation.StringRes
import com.example.movie_catalog.R

enum class Kit(@StringRes var nameKit:Int = 0,
               var displayText:String = "",
               var query:String = "",
               var genreID: Int = 0,
               var countryID:Int = 0) {
    PREMIERES (nameKit = R.string.premieres_kit),
    POPULAR (nameKit = R.string.popular_kit, query = "TOP_100_POPULAR_FILMS"),
    SERIALS (nameKit = R.string.serials_kit),
    TOP250 (nameKit = R.string.top_kit,query = "TOP_250_BEST_FILMS"),
    RANDOM1 (nameKit = R.string.random1_kit),
    RANDOM2 (nameKit = R.string.random2_kit),
    SEARCH (nameKit = R.string.search_kit),
    SIMILAR (nameKit = R.string.similar_kit),
    COLLECTION,
    PERSON
}