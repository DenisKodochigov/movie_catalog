package com.example.movie_catalog.entity.enumApp

import com.example.movie_catalog.App
import com.example.movie_catalog.R

enum class Kit(var nameKit:String, var query:String, var genreID: Int, var countryID:Int) {
    PREMIERES (App.context.getString(R.string.premieres),"",0,0),
    POPULAR (App.context.getString(R.string.popular), "TOP_100_POPULAR_FILMS",0,0),
    SERIALS (App.context.getString(R.string.serials),"",0,0),
    TOP250 (App.context.getString(R.string.top),"TOP_250_BEST_FILMS",0,0),
    RANDOM1 (App.context.getString(R.string.random1),"",0,0),
    RANDOM2 (App.context.getString(R.string.random2), "",0,0),
    SEARCH (App.context.getString(R.string.search_kit), "",0,0),
    VIEWED (App.context.getString(R.string.viewed_kit), "",0,0),
    BOOKMARK (App.context.getString(R.string.bookmark_kit), "",0,0),
}