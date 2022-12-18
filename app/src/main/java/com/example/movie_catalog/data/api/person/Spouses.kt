package com.example.movie_catalog.data.api.person

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Spouses (
    @Json(name = "personId") val personId:Int? = null,
    @Json(name = "name") val name:String? = null,
    @Json(name = "divorced") val divorced:Boolean? = null,
    @Json(name = "divorcedReason") val divorcedReason:String? = null,
    @Json(name = "sex") val sex:String? = null,
    @Json(name = "children") val children:Int? = null,
    @Json(name = "webUrl") val webUrl:String? = null,
    @Json(name = "relation") val relation:String? = null,
)