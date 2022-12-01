package com.example.movie_catalog.entity

import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO

data class Person(
    var personId:Int? = null,
    val nameRu:String? = null,
    val nameEn:String? = null,
    val posterUrl:String? = null,
    val age:String? = null,
    val hasAwards:Int? = null,
    val profession:String? = null,
    val films:List<Film> = emptyList(),
) {
    fun convertor(personDTO: PersonDTO): Person{
        return Person(
            personId = personDTO.staffId,
            nameRu = personDTO.nameRu,
            nameEn = personDTO.nameEn,
            posterUrl = personDTO.posterUrl,
            age = null,
            hasAwards = null,
            profession = personDTO.professionText,
            films = emptyList()
        )
    }
}
