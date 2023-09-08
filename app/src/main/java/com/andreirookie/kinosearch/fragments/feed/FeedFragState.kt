package com.andreirookie.kinosearch.fragments.feed

import com.andreirookie.kinosearch.models.Movie

sealed interface FeedFragState {
    data object Loading : FeedFragState
    data object Init : FeedFragState
    data object Error : FeedFragState
    data class TopMovies(val popMovies: List<Movie>) : FeedFragState
    data class FavoriteMovies(val favMovies: List<Movie>) : FeedFragState
}


