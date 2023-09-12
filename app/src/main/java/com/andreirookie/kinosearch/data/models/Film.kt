package com.andreirookie.kinosearch.data.models

data class Film(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val issueYear: Int = 0,
    val genre: String = "",
    val country: String = "",
    val description: String = "",
    val shortDescription: String = "",
    val isLiked: Boolean = false
)
