package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.domain.FilmFeedModel
import javax.inject.Inject

interface InMemoryRepository {
    suspend fun getPopFilms(): List<FilmFeedModel>
    suspend fun getFavFilms(): List<FilmFeedModel>
    suspend fun saveFilms(list: List<FilmFeedModel>)
}

class InMemoryRepositoryImpl @Inject constructor(
    private val cache: FilmsCache
) : InMemoryRepository {

    override suspend fun getPopFilms(): List<FilmFeedModel> {
        return cache.getPopFilms()
    }

    override suspend fun getFavFilms(): List<FilmFeedModel> {
        return cache.getFavFilms()
    }

    override suspend fun saveFilms(list: List<FilmFeedModel>) {
        cache.saveFilms(list)
    }
}