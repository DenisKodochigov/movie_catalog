// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.example.movie_catalog.`data`.api.home.filter

import com.example.movie_catalog.entity.home.Country
import com.example.movie_catalog.entity.home.Genre
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import java.lang.reflect.Constructor
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.emptySet
import kotlin.jvm.Volatile
import kotlin.text.buildString

public class FilterFilmDTOJsonAdapter(
  moshi: Moshi
) : JsonAdapter<FilterFilmDTO>() {
  private val options: JsonReader.Options = JsonReader.Options.of("kinopoiskId", "imdbId", "nameRu",
      "nameEn", "nameOriginal", "countries", "genres", "ratingKinopoisk", "ratingImdb", "year",
      "type", "posterUrl", "posterUrlPreview")

  private val nullableIntAdapter: JsonAdapter<Int?> = moshi.adapter(Int::class.javaObjectType,
      emptySet(), "kinopoiskId")

  private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java,
      emptySet(), "imdbId")

  private val listOfCountryAdapter: JsonAdapter<List<Country>> =
      moshi.adapter(Types.newParameterizedType(List::class.java, Country::class.java), emptySet(),
      "countries")

  private val listOfGenreAdapter: JsonAdapter<List<Genre>> =
      moshi.adapter(Types.newParameterizedType(List::class.java, Genre::class.java), emptySet(),
      "genres")

  private val nullableDoubleAdapter: JsonAdapter<Double?> =
      moshi.adapter(Double::class.javaObjectType, emptySet(), "ratingKinopoisk")

  @Volatile
  private var constructorRef: Constructor<FilterFilmDTO>? = null

  public override fun toString(): String = buildString(35) {
      append("GeneratedJsonAdapter(").append("FilterFilmDTO").append(')') }

  public override fun fromJson(reader: JsonReader): FilterFilmDTO {
    var kinopoiskId: Int? = null
    var imdbId: String? = null
    var nameRu: String? = null
    var nameEn: String? = null
    var nameOriginal: String? = null
    var countries: List<Country>? = null
    var genres: List<Genre>? = null
    var ratingKinopoisk: Double? = null
    var ratingImdb: Double? = null
    var year: Int? = null
    var type: String? = null
    var posterUrl: String? = null
    var posterUrlPreview: String? = null
    var mask0 = -1
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> {
          kinopoiskId = nullableIntAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 0).inv()
          mask0 = mask0 and 0xfffffffe.toInt()
        }
        1 -> {
          imdbId = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 1).inv()
          mask0 = mask0 and 0xfffffffd.toInt()
        }
        2 -> {
          nameRu = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 2).inv()
          mask0 = mask0 and 0xfffffffb.toInt()
        }
        3 -> {
          nameEn = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 3).inv()
          mask0 = mask0 and 0xfffffff7.toInt()
        }
        4 -> {
          nameOriginal = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 4).inv()
          mask0 = mask0 and 0xffffffef.toInt()
        }
        5 -> {
          countries = listOfCountryAdapter.fromJson(reader) ?:
              throw Util.unexpectedNull("countries", "countries", reader)
          // $mask = $mask and (1 shl 5).inv()
          mask0 = mask0 and 0xffffffdf.toInt()
        }
        6 -> {
          genres = listOfGenreAdapter.fromJson(reader) ?: throw Util.unexpectedNull("genres",
              "genres", reader)
          // $mask = $mask and (1 shl 6).inv()
          mask0 = mask0 and 0xffffffbf.toInt()
        }
        7 -> {
          ratingKinopoisk = nullableDoubleAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 7).inv()
          mask0 = mask0 and 0xffffff7f.toInt()
        }
        8 -> {
          ratingImdb = nullableDoubleAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 8).inv()
          mask0 = mask0 and 0xfffffeff.toInt()
        }
        9 -> {
          year = nullableIntAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 9).inv()
          mask0 = mask0 and 0xfffffdff.toInt()
        }
        10 -> {
          type = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 10).inv()
          mask0 = mask0 and 0xfffffbff.toInt()
        }
        11 -> {
          posterUrl = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 11).inv()
          mask0 = mask0 and 0xfffff7ff.toInt()
        }
        12 -> {
          posterUrlPreview = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 12).inv()
          mask0 = mask0 and 0xffffefff.toInt()
        }
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    if (mask0 == 0xffffe000.toInt()) {
      // All parameters with defaults are set, invoke the constructor directly
      return  FilterFilmDTO(
          kinopoiskId = kinopoiskId,
          imdbId = imdbId,
          nameRu = nameRu,
          nameEn = nameEn,
          nameOriginal = nameOriginal,
          countries = countries as List<Country>,
          genres = genres as List<Genre>,
          ratingKinopoisk = ratingKinopoisk,
          ratingImdb = ratingImdb,
          year = year,
          type = type,
          posterUrl = posterUrl,
          posterUrlPreview = posterUrlPreview
      )
    } else {
      // Reflectively invoke the synthetic defaults constructor
      @Suppress("UNCHECKED_CAST")
      val localConstructor: Constructor<FilterFilmDTO> = this.constructorRef ?:
          FilterFilmDTO::class.java.getDeclaredConstructor(Int::class.javaObjectType,
          String::class.java, String::class.java, String::class.java, String::class.java,
          List::class.java, List::class.java, Double::class.javaObjectType,
          Double::class.javaObjectType, Int::class.javaObjectType, String::class.java,
          String::class.java, String::class.java, Int::class.javaPrimitiveType,
          Util.DEFAULT_CONSTRUCTOR_MARKER).also { this.constructorRef = it }
      return localConstructor.newInstance(
          kinopoiskId,
          imdbId,
          nameRu,
          nameEn,
          nameOriginal,
          countries,
          genres,
          ratingKinopoisk,
          ratingImdb,
          year,
          type,
          posterUrl,
          posterUrlPreview,
          mask0,
          /* DefaultConstructorMarker */ null
      )
    }
  }

  public override fun toJson(writer: JsonWriter, value_: FilterFilmDTO?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("kinopoiskId")
    nullableIntAdapter.toJson(writer, value_.kinopoiskId)
    writer.name("imdbId")
    nullableStringAdapter.toJson(writer, value_.imdbId)
    writer.name("nameRu")
    nullableStringAdapter.toJson(writer, value_.nameRu)
    writer.name("nameEn")
    nullableStringAdapter.toJson(writer, value_.nameEn)
    writer.name("nameOriginal")
    nullableStringAdapter.toJson(writer, value_.nameOriginal)
    writer.name("countries")
    listOfCountryAdapter.toJson(writer, value_.countries)
    writer.name("genres")
    listOfGenreAdapter.toJson(writer, value_.genres)
    writer.name("ratingKinopoisk")
    nullableDoubleAdapter.toJson(writer, value_.ratingKinopoisk)
    writer.name("ratingImdb")
    nullableDoubleAdapter.toJson(writer, value_.ratingImdb)
    writer.name("year")
    nullableIntAdapter.toJson(writer, value_.year)
    writer.name("type")
    nullableStringAdapter.toJson(writer, value_.type)
    writer.name("posterUrl")
    nullableStringAdapter.toJson(writer, value_.posterUrl)
    writer.name("posterUrlPreview")
    nullableStringAdapter.toJson(writer, value_.posterUrlPreview)
    writer.endObject()
  }
}
