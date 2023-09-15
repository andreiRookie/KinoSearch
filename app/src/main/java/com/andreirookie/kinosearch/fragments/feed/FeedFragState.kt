package com.andreirookie.kinosearch.fragments.feed

import com.andreirookie.kinosearch.domain.FilmFeedModel

sealed interface FeedFragState {
    data object Loading : FeedFragState
    data object Init : FeedFragState
    data object Error : FeedFragState
    data class PopularFilms(val popFilms: List<FilmFeedModel>) : FeedFragState
    data class FavoriteFilms(val favFilms: List<FilmFeedModel>) : FeedFragState
}


