package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetPopFilmsUseCase {
    suspend operator fun invoke(): List<FilmFeedModel>
}

private const val FIRST_PAGE = 1

class GetPopFilmsUseCaseImpl @Inject constructor(
    private val inMemoryRepository: InMemoryRepository,
    private val dbRepository: DbRepository,
    private val networkRepository: NetworkRepository
) : GetPopFilmsUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend operator fun invoke(): List<FilmFeedModel> {
        return flow {
            emit(inMemoryRepository.getPopFilms())
            emit(dbRepository.getPopFilms())
            emit(networkRepository.loadPopularFilmsByPage(FIRST_PAGE))
        }
            .flatMapConcat {
                flowOf(it)
            }
            .flowOn(Dispatchers.IO)
//            .filterNot { it.isEmpty() }
            .first { it.isNotEmpty() }
            .toList()
    }
}