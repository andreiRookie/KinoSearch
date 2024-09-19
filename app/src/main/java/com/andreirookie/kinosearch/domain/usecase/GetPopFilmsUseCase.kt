package com.andreirookie.kinosearch.domain.usecase


import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetPopFilmsUseCase {
    operator fun invoke(): Single<List<FilmFeedModel>>
}

private const val FIRST_PAGE = 1

class GetPopFilmsUseCaseImpl @Inject constructor(
    private val dbRepository: DbRepository,
    private val networkRepository: NetworkRepository
) : GetPopFilmsUseCase {

    override operator fun invoke(): Single<List<FilmFeedModel>> {
        val dbList = dbRepository.getPopFilms()
        val networkList = networkRepository.loadPopularFilmsByPage(FIRST_PAGE)
        return Observable.concat(dbList, networkList)
            .filter { list -> list.isNotEmpty() }
            .first(emptyList())
    }
}