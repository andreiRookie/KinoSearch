package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

interface InMemoryRepository {
    suspend fun getPopFilms(): List<FilmFeedModel>
    suspend fun getFavFilms(): List<FilmFeedModel>
}

class InMemoryRepositoryImpl @Inject constructor(
    private val dbRepository: DbRepository,
    private val coroutineScope: CoroutineScope
) : InMemoryRepository {

    private var popFilmList: List<FilmFeedModel> = emptyList()

    private val favFilmList: List<FilmFeedModel>
        get() = popFilmList.filter { film -> film.isLiked }

    override suspend fun getPopFilms(): List<FilmFeedModel> {
        return popFilmList
    }

    override suspend fun getFavFilms(): List<FilmFeedModel> {
        return favFilmList
    }
}