package com.andreirookie.kinosearch.domain

data class FilmInfo(
    val filmDetails: FilmDetailsModel,
    val director: String,
    val actors: String
)

enum class ProfessionKey(val value: String) {
    DIRECTOR("DIRECTOR"),
    ACTOR("ACTOR")
}
