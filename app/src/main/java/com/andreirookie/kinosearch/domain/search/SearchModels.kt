package com.andreirookie.kinosearch.domain.search

import com.andreirookie.kinosearch.domain.FilmFeedModel

sealed interface SearchState {
    data object Init : SearchState
    data class Result(val list: List<FilmFeedModel>) : SearchState
    data object Error : SearchState
    data object Empty : SearchState
}
data class SearchResult(
    val list: List<FilmFeedModel> = emptyList()
)
