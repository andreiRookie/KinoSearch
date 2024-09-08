package com.andreirookie.kinosearch.domain.search

import com.andreirookie.kinosearch.data.net.NetworkRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val networkRepo: NetworkRepository
) {
    suspend operator fun invoke(query: String): SearchResult {
        val result = networkRepo.searchFilmByKeyword(query)
        return SearchResult(result)
    }
}