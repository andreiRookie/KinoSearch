package com.andreirookie.kinosearch.data.cache

data class Film(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val issueYear: Int = 0,
    val genre: String ="",
    val country: String ="",
    val description: String = "Description",
    val isLiked: Boolean = false
)
