package com.andreirookie.kinosearch.data.models

import com.squareup.moshi.Json

data class PopularFilmsApiResponse(
    @field:Json(name = "films")
    val films: List<FilmNetModel>
)

data class FilmNetModel(
    @field:Json(name = "filmId")
    val kinopoiskId: Int,
    @field:Json(name = "nameRu")
    val nameRu: String?,
    @field:Json(name = "nameEn")
    val nameEn: String?,
    @field:Json(name = "posterUrlPreview")
    val posterUrlPreview: String,
    @field:Json(name = "year")
    val year: Int?,
    @field:Json(name = "countries")
    val countries: List<Country>,
    @field:Json(name = "genres")
    val genres: List<Genre>
)

data class Genre(
    val genre: String = ""
)
data class Country(
    val country: String = ""
)
