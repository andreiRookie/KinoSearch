package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

interface LikeFilmUseCase {
    operator fun invoke(film: FilmFeedModel): Completable
}

class LikeFilmUseCaseImpl @Inject constructor(
    private val dbRepository: DbRepository
): LikeFilmUseCase {
    override operator fun invoke(film: FilmFeedModel): Completable  {
        return dbRepository.likeFilm(film)
    }
}