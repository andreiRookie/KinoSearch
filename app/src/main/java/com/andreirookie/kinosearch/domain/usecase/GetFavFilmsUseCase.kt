package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetFavFilmsUseCase {
    operator fun invoke(): Single<List<FilmFeedModel>>
}

class GetFavFilmsUseCaseImpl @Inject constructor(
    private val dbRepository: DbRepository
) : GetFavFilmsUseCase {

    override operator fun invoke(): Single<List<FilmFeedModel>> {
        return dbRepository.getFavFilms()
    }
}