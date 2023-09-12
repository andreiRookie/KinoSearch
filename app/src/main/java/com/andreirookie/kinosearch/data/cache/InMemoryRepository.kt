package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.data.models.Film
import javax.inject.Inject

interface InMemoryRepository {
    suspend fun getPopFilms(): List<Film>
    suspend fun getFavFilms(): List<Film>
}

class InMemoryRepositoryImpl @Inject constructor() : InMemoryRepository {

    private val list = listOf(
        Film(1, "Film #1", genre = "Comedy", issueYear = 2001),
        Film(2, "Film #2", genre = "Horror", issueYear = 2002, isLiked = true),
        Film(3, "Film #3", genre = "Action", issueYear = 2003),
        Film(10, "Film #4", genre = "Sci-fi", issueYear = 2004),
        Film(11, "Film #5", genre = "Adventure", issueYear = 2005),
        Film(15, "Film #6", genre = "Sci-fi", issueYear = 2006,isLiked = true)
    )

    override suspend fun getPopFilms(): List<Film> {
        return list
    }

    override suspend fun getFavFilms(): List<Film> {
        return list.filter { film -> film.isLiked }
    }
}