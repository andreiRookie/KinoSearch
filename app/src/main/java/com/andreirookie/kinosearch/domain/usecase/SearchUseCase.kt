package com.andreirookie.kinosearch.domain.usecase

import com.andreirookie.kinosearch.data.net.NetworkRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val networkRepo: NetworkRepository
) {
    operator fun invoke(query: String): Single<SearchResult> {
        return networkRepo.searchFilmByKeyword(query)
            .map { list -> SearchResult(list) }
    }
}