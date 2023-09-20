package com.andreirookie.kinosearch.domain.search

import com.andreirookie.kinosearch.data.net.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val networkRepo: NetworkRepository
) {
    suspend fun execute(query: String): SearchResult {
        return withContext(Dispatchers.Default) {
            doNetSearch(query)
        }
    }
    private suspend fun doNetSearch(query: String): SearchResult {
        return SearchResult(networkRepo.searchFilmByKeyword(query))
    }
}