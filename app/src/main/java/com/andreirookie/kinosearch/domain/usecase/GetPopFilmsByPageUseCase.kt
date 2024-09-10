package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetPopFilmsByPageUseCase {
    suspend operator fun invoke(page: Int): List<FilmFeedModel>
}

class GetPopFilmsByPageUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : GetPopFilmsByPageUseCase {

    override suspend operator fun invoke(page: Int): List<FilmFeedModel> {
        return withContext(Dispatchers.IO) {
            networkRepository.loadPopularFilmsByPage(page).toList()
        }
    }
}