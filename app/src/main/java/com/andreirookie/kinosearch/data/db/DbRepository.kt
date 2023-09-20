package com.andreirookie.kinosearch.data.db

import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.FilmFeedModel.Companion.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DbRepository {
    suspend fun requestAndSaveAll()
    suspend fun getPopFilms(): Flow<List<FilmFeedModel>>
    suspend fun getFavFilms(): Flow<List<FilmFeedModel>>
    suspend fun likeById(film: FilmFeedModel)
}

class DbRepositoryImpl @Inject constructor(
    private val dao: FilmDao,
    private val dispatcherIo: CoroutineDispatcher,
    private val networkRepository: NetworkRepository
) : DbRepository {
    override suspend fun getPopFilms(): Flow<List<FilmFeedModel>> {
        return flow {
            emit(withContext(dispatcherIo) {
                dao.queryAll().map { it.asModel() }
            })
        }
    }
    override suspend fun getFavFilms(): Flow<List<FilmFeedModel>> {
        return flow {
            emit(withContext(dispatcherIo) {
                dao.queryAllFavorites().map { it.asModel() }
            })
        }
    }
    override suspend fun requestAndSaveAll() {
        withContext(dispatcherIo) {
            val popFilms = networkRepository.loadPopularFilms()
            if (popFilms.isNotEmpty()) {
                dao.insertAll(popFilms.asEntityList())
            }
        }
    }
    override suspend fun likeById(film: FilmFeedModel) {
        withContext(dispatcherIo) {
            dao.insert(film.toEntity())
            dao.likeFilmById(film.id)
        }
    }

    private fun List<FilmFeedModel>.asEntityList(): List<FilmFeedEntity> {
        return this.map { it.toEntity() }
    }
}