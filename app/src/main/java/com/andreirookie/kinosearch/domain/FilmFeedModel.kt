package com.andreirookie.kinosearch.domain

data class FilmFeedModel(
    val id: Int,
    val title: String,
    val posterUrlPreview: String = "",
    val issueYear: String = "",
    val genre: String = "",
    val isLiked: Boolean = false
)
