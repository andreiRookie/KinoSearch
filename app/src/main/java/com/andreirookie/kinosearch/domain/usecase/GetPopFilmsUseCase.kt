package com.andreirookie.kinosearch.domain.usecase


import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface GetPopFilmsUseCase {
    operator fun invoke(): Single<List<FilmFeedModel>>
}

private const val FIRST_PAGE = 1

class GetPopFilmsUseCaseImpl @Inject constructor(
    private val inMemoryRepository: InMemoryRepository,
    private val dbRepository: DbRepository,
    private val networkRepository: NetworkRepository
) : GetPopFilmsUseCase {

    override operator fun invoke(): Single<List<FilmFeedModel>> {
        val cacheList = inMemoryRepository.getPopFilms()
        val dbList = dbRepository.getPopFilms()
        val networkList = networkRepository.loadPopularFilmsByPage(FIRST_PAGE)

        return Observable.concat(cacheList, dbList, networkList)
            .filter { list -> list.isNotEmpty() }
            .first(emptyList())
            .subscribeOn(Schedulers.io())
    }
}