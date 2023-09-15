package com.andreirookie.kinosearch.fragments.film

import com.andreirookie.kinosearch.domain.FilmInfo


sealed interface FilmDetailsFragState {
    data object Init : FilmDetailsFragState
    data object Loading : FilmDetailsFragState
    data class Data(val filmInfo: FilmInfo) : FilmDetailsFragState
    data object Error : FilmDetailsFragState
}