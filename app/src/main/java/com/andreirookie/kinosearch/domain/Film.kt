package com.andreirookie.kinosearch.domain

data class Film(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val issueYear: String = "",
    val genre: String = "",
    val country: String = "",
    val filmLength: String = "",
    val description: String = "",
    val isLiked: Boolean = false
)
