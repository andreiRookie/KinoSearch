package com.andreirookie.kinosearch.domain

data class FilmDetailsModel(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val country: String = "",
    val filmLength: String = "",
    val description: String = ""
)
