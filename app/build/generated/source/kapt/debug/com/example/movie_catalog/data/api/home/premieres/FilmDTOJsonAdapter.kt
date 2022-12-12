// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.example.movie_catalog.`data`.api.home.premieres

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
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.emptySet
import kotlin.jvm.Volatile
import kotlin.text.buildString

public class FilmDTOJsonAdapter(
  moshi: Moshi
) : JsonAdapter<FilmDTO>() {
  private val options: JsonReader.Options = JsonReader.Options.of("kinopoiskId", "nameRu", "nameEn",
      "year", "posterUrl", "posterUrlPreview", "countries", "genres", "duration", "premiereRu")

  private val nullableIntAdapter: JsonAdapter<Int?> = moshi.adapter(Int::class.javaObjectType,
      emptySet(), "kinopoiskId")

  private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java,
      emptySet(), "nameRu")

  private val listOfCountryAdapter: JsonAdapter<List<Country>> =
      moshi.adapter(Types.newParameterizedType(List::class.java, Country::class.java), emptySet(),
      "countries")

  private val listOfGenreAdapter: JsonAdapter<List<Genre>> =
      moshi.adapter(Types.newParameterizedType(List::class.java, Genre::class.java), emptySet(),
      "genres")

  @Volatile
  private var constructorRef: Constructor<FilmDTO>? = null

  public override fun toString(): String = buildString(29) {
      append("GeneratedJsonAdapter(").append("FilmDTO").append(')') }

  public override fun fromJson(reader: JsonReader): FilmDTO {
    var kinopoiskId: Int? = null
    var nameRu: String? = null
    var nameEn: String? = null
    var year: Int? = null
    var posterUrl: String? = null
    var posterUrlPreview: String? = null
    var countries: List<Country>? = null
    var genres: List<Genre>? = null
    var duration: Int? = null
    var premiereRu: String? = null
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
          nameRu = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 1).inv()
          mask0 = mask0 and 0xfffffffd.toInt()
        }
        2 -> {
          nameEn = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 2).inv()
          mask0 = mask0 and 0xfffffffb.toInt()
        }
        3 -> {
          year = nullableIntAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 3).inv()
          mask0 = mask0 and 0xfffffff7.toInt()
        }
        4 -> {
          posterUrl = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 4).inv()
          mask0 = mask0 and 0xffffffef.toInt()
        }
        5 -> {
          posterUrlPreview = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 5).inv()
          mask0 = mask0 and 0xffffffdf.toInt()
        }
        6 -> {
          countries = listOfCountryAdapter.fromJson(reader) ?:
              throw Util.unexpectedNull("countries", "countries", reader)
          // $mask = $mask and (1 shl 6).inv()
          mask0 = mask0 and 0xffffffbf.toInt()
        }
        7 -> {
          genres = listOfGenreAdapter.fromJson(reader) ?: throw Util.unexpectedNull("genres",
              "genres", reader)
          // $mask = $mask and (1 shl 7).inv()
          mask0 = mask0 and 0xffffff7f.toInt()
        }
        8 -> {
          duration = nullableIntAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 8).inv()
          mask0 = mask0 and 0xfffffeff.toInt()
        }
        9 -> {
          premiereRu = nullableStringAdapter.fromJson(reader)
          // $mask = $mask and (1 shl 9).inv()
          mask0 = mask0 and 0xfffffdff.toInt()
        }
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    if (mask0 == 0xfffffc00.toInt()) {
      // All parameters with defaults are set, invoke the constructor directly
      return  FilmDTO(
          kinopoiskId = kinopoiskId,
          nameRu = nameRu,
          nameEn = nameEn,
          year = year,
          posterUrl = posterUrl,
          posterUrlPreview = posterUrlPreview,
          countries = countries as List<Country>,
          genres = genres as List<Genre>,
          duration = duration,
          premiereRu = premiereRu
      )
    } else {
      // Reflectively invoke the synthetic defaults constructor
      @Suppress("UNCHECKED_CAST")
      val localConstructor: Constructor<FilmDTO> = this.constructorRef ?:
          FilmDTO::class.java.getDeclaredConstructor(Int::class.javaObjectType, String::class.java,
          String::class.java, Int::class.javaObjectType, String::class.java, String::class.java,
          List::class.java, List::class.java, Int::class.javaObjectType, String::class.java,
          Int::class.javaPrimitiveType, Util.DEFAULT_CONSTRUCTOR_MARKER).also {
          this.constructorRef = it }
      return localConstructor.newInstance(
          kinopoiskId,
          nameRu,
          nameEn,
          year,
          posterUrl,
          posterUrlPreview,
          countries,
          genres,
          duration,
          premiereRu,
          mask0,
          /* DefaultConstructorMarker */ null
      )
    }
  }

  public override fun toJson(writer: JsonWriter, value_: FilmDTO?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("kinopoiskId")
    nullableIntAdapter.toJson(writer, value_.kinopoiskId)
    writer.name("nameRu")
    nullableStringAdapter.toJson(writer, value_.nameRu)
    writer.name("nameEn")
    nullableStringAdapter.toJson(writer, value_.nameEn)
    writer.name("year")
    nullableIntAdapter.toJson(writer, value_.year)
    writer.name("posterUrl")
    nullableStringAdapter.toJson(writer, value_.posterUrl)
    writer.name("posterUrlPreview")
    nullableStringAdapter.toJson(writer, value_.posterUrlPreview)
    writer.name("countries")
    listOfCountryAdapter.toJson(writer, value_.countries)
    writer.name("genres")
    listOfGenreAdapter.toJson(writer, value_.genres)
    writer.name("duration")
    nullableIntAdapter.toJson(writer, value_.duration)
    writer.name("premiereRu")
    nullableStringAdapter.toJson(writer, value_.premiereRu)
    writer.endObject()
  }
}
