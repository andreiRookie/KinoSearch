package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetPopFilmsByPageUseCase {

    operator fun invoke(page: Int): Single<List<FilmFeedModel>>
}

class GetPopFilmsByPageUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : GetPopFilmsByPageUseCase {

    override operator fun invoke(page: Int): Single<List<FilmFeedModel>> {
        return networkRepository.loadPopularFilmsByPage(page)
            .single(emptyList())
    }
}