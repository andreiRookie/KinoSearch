package com.andreirookie.kinosearch.fragments.film

import com.andreirookie.kinosearch.data.models.Film

sealed interface FilmDetailsFragState {
    data object Init : FilmDetailsFragState
    data object Loading : FilmDetailsFragState
    data class Data(val film: Film) : FilmDetailsFragState
    data object Error : FilmDetailsFragState
}