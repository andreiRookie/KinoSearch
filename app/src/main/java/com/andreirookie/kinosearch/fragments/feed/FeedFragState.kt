package com.andreirookie.kinosearch.fragments.feed

import com.andreirookie.kinosearch.data.cache.Film

sealed interface FeedFragState {
    data object Loading : FeedFragState
    data object Init : FeedFragState
    data object Error : FeedFragState
    data class TopFilms(val popFilms: List<Film>) : FeedFragState
    data class FavoriteFilms(val favFilms: List<Film>) : FeedFragState
}


