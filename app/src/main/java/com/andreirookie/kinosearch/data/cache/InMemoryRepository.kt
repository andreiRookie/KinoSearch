package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.domain.FilmFeedModel
import javax.inject.Inject

interface InMemoryRepository {
    suspend fun getPopFilms(): List<FilmFeedModel>
    suspend fun getFavFilms(): List<FilmFeedModel>
}

class InMemoryRepositoryImpl @Inject constructor() : InMemoryRepository {

    private val list = listOf(
        FilmFeedModel(1, "Film #1", genre = "Comedy", issueYear = "2001"),
        FilmFeedModel(2, "Film #2", genre = "Horror", issueYear = "2002", isLiked = true),
        FilmFeedModel(3, "Film #3", genre = "Action", issueYear = "2003"),
        FilmFeedModel(10, "Film #4", genre = "Sci-fi", issueYear = "2004"),
        FilmFeedModel(11, "Film #5", genre = "Adventure", issueYear = "2005"),
        FilmFeedModel(15, "Film #6", genre = "Sci-fi", issueYear = "2006",isLiked = true)
    )

    override suspend fun getPopFilms(): List<FilmFeedModel> {
        return list
    }

    override suspend fun getFavFilms(): List<FilmFeedModel> {
        return list.filter { film -> film.isLiked }
    }
}