package com.andreirookie.kinosearch.fragments.feed

sealed interface FilmFeedState<T> {
    class Loading<T> : FilmFeedState<T>
    class Init<T> : FilmFeedState<T>
    data class Error<T>(val ex: Throwable) : FilmFeedState<T>
    data class Data<T>(val data: T) : FilmFeedState<T>
}


