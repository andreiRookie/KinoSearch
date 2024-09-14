package com.andreirookie.kinosearch.data.db

import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DbRepository {
    fun getPopFilms(): Observable<List<FilmFeedModel>>
    suspend fun getFavFilms(): List<FilmFeedModel>
    suspend fun likeFilm(film: FilmFeedModel)
    fun insertAll(list: List<FilmFeedModel>)
}

class DbRepositoryImpl @Inject constructor(
    private val dao: FilmDao,
    private val inMemoryRepository: InMemoryRepository
) : DbRepository {
    override fun getPopFilms(): Observable<List<FilmFeedModel>> {
        return Observable.fromCallable { dao.queryAll().map { it.asModel() } }
    }

    override suspend fun getFavFilms(): List<FilmFeedModel> {
        return withContext(Dispatchers.IO) {
            dao.queryAllFavorites().map { it.asModel() }
        }
    }

    override fun insertAll(list: List<FilmFeedModel>) {

            if (list.isNotEmpty()) {
                dao.insertAll(list.asEntityList())
                inMemoryRepository.saveFilms(list)
            }

    }

    override suspend fun likeFilm(film: FilmFeedModel) {
        withContext(Dispatchers.IO) {
            dao.insert(film.asEntity())
            dao.likeFilmById(film.id)
        }
    }

    private fun List<FilmFeedModel>.asEntityList(): List<FilmFeedEntity> {
        return this.map { it.asEntity() }
    }
}