package com.andreirookie.kinosearch.domain

import com.andreirookie.kinosearch.data.db.FilmFeedEntity

data class FilmFeedModel(
    val id: Int,
    val title: String,
    val posterUrlPreview: String = "",
    val issueYear: String = "",
    val genre: String = "",
    val isLiked: Boolean = false
) {

    fun asEntity(): FilmFeedEntity {
        return FilmFeedEntity(
            filmId = id,
            title = title,
            posterUrlPreview = posterUrlPreview,
            issueYear = issueYear,
            genre = genre,
            isLiked = isLiked
        )
    }
}
