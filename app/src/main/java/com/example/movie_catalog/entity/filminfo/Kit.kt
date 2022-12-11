package com.example.movie_catalog.entity.filminfo

import com.example.movie_catalog.App
import com.example.movie_catalog.R

enum class Kit(var nameKit:String, var query:String, var genreID: Int, var countryID:Int) {
    PREMIERES (App.context.getString(R.string.premieres),"",1,1),
    POPULAR (App.context.getString(R.string.popular), "TOP_100_POPULAR_FILMS",1,1),
    SERIALS (App.context.getString(R.string.serials),"",1,1),
    RANDOM1 (App.context.getString(R.string.top),"",1,1),
    RANDOM2 (App.context.getString(R.string.random1),"",1,1),
    TOP250 (App.context.getString(R.string.random2), "TOP_250_BEST_FILMS",1,1);
}