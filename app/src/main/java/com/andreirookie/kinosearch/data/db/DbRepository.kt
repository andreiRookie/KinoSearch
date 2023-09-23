package com.andreirookie.kinosearch.data.db

import android.util.Log
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DbRepository {
    suspend fun requestAndSaveAll()
    suspend fun requestAndSaveAllByPage(page: Int)
    suspend fun getPopFilms(): Flow<List<FilmFeedModel>>
    suspend fun getFavFilms(): Flow<List<FilmFeedModel>>
    suspend fun likeFilm(film: FilmFeedModel)
}

class DbRepositoryImpl @Inject constructor(
    private val dao: FilmDao,
    private val dispatcherIo: CoroutineDispatcher,
    private val networkRepository: NetworkRepository
) : DbRepository {
    override suspend fun getPopFilms(): Flow<List<FilmFeedModel>> {
        return flow {
            emit(withContext(dispatcherIo) {
                Log.e("AAA", "getPopFilms(): ${dao.queryAll()}")
                dao.queryAll().map { it.asModel() }
            })
        }
    }
    override suspend fun getFavFilms(): Flow<List<FilmFeedModel>> {
        return flow {
            emit(withContext(dispatcherIo) {
                Log.e("AAA", "getFavFilms(): ${dao.queryAllFavorites()}")
                dao.queryAllFavorites().map { it.asModel() }
            })
        }
    }

    override suspend fun requestAndSaveAllByPage(page: Int) {
        withContext(dispatcherIo) {
            val popFilms = networkRepository.loadPopularFilmsByPage(page)
            if (popFilms.isNotEmpty()) {
                dao.insertAll(popFilms.asEntityList())
            }
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
    override suspend fun likeFilm(film: FilmFeedModel) {
        withContext(dispatcherIo) {
            dao.insert(film.asEntity())
            dao.likeFilmById(film.id)
        }
    }

    private fun List<FilmFeedModel>.asEntityList(): List<FilmFeedEntity> {
        return this.map { it.asEntity() }
    }
}