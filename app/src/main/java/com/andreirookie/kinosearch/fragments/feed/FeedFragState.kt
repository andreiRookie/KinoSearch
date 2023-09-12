package com.andreirookie.kinosearch.fragments.feed

import com.andreirookie.kinosearch.data.models.Film

sealed interface FeedFragState {
    data object Loading : FeedFragState
    data object Init : FeedFragState
    data object Error : FeedFragState
    data class PopularFilms(val popFilms: List<Film>) : FeedFragState
    data class FavoriteFilms(val favFilms: List<Film>) : FeedFragState
}


