package com.andreirookie.kinosearch.data

import com.andreirookie.kinosearch.models.Movie
import javax.inject.Inject

interface InMemoryRepository {
    suspend fun getPopMovies(): List<Movie>
    suspend fun getFavMovies(): List<Movie>
}

class InMemoryRepositoryImpl @Inject constructor() : InMemoryRepository {

    private val list = listOf(
        Movie(1, "Movie #1", genre = "Comedy", issueYear = 2001),
        Movie(2, "Movie #2", genre = "Horror", issueYear = 2002, isLiked = true),
        Movie(3, "Movie #3", genre = "Action", issueYear = 2003),
        Movie(10, "Movie #4", genre = "Sci-fi", issueYear = 2004),
        Movie(11, "Movie #5", genre = "Adventure", issueYear = 2005),
        Movie(15, "Movie #6", genre = "Sci-fi", issueYear = 2006,isLiked = true)
    )

    override suspend fun getPopMovies(): List<Movie> {
        return list
    }

    override suspend fun getFavMovies(): List<Movie> {
        return list.filter { movie -> movie.isLiked }
    }

}