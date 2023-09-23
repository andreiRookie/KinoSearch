package com.andreirookie.kinosearch.fragments.feed

import java.lang.Exception

sealed interface FeedFragState {
    data object Loading : FeedFragState
    data object Init : FeedFragState
    data class Error(val ex: Exception) : FeedFragState
}
sealed interface ScreenEvent {
    data class Error(val msg: String) : ScreenEvent
}


