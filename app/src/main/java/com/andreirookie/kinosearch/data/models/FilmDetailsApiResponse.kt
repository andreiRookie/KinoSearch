package com.andreirookie.kinosearch.data.models

import com.squareup.moshi.Json

data class FilmDetailsNetModel(
    @field:Json(name = "kinopoiskId")
    val kinopoiskId: Int,
    @field:Json(name = "nameRu")
    val nameRu: String?,
    @field:Json(name = "nameEn")
    val nameEn: String?,
    @field:Json(name = "posterUrl")
    val posterUrl: String,
    @field:Json(name = "year")
    val year: Int?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "countries")
    val countries: List<Country>,
    @field:Json(name = "genres")
    val genres: List<Genre>,
    @field:Json(name = "filmLength")
    val filmLength: Int?
)