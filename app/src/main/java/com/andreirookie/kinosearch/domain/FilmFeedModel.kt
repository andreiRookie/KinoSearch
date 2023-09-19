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
    companion object {
        fun FilmFeedModel.toEntity(): FilmFeedEntity {
            return FilmFeedEntity(
                filmId = this.id,
                title = this.title,
                posterUrlPreview = this.posterUrlPreview,
                issueYear = this.issueYear,
                genre = this.genre,
                isLiked = this.isLiked
            )
        }
    }
}
