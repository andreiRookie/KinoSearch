package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.domain.FilmFeedModel
import javax.inject.Inject

interface FilmsCache {
    fun getPopFilms(): List<FilmFeedModel>
    fun getFavFilms(): List<FilmFeedModel>
    fun saveFilms(list: List<FilmFeedModel>)
}

class FilmsCacheImpl @Inject constructor() : FilmsCache {

    private var cache: MutableSet<FilmFeedModel> = mutableSetOf()

    private val popFilmList: List<FilmFeedModel>
        get() = cache.toList()

    private val favFilmList: List<FilmFeedModel>
        get() = popFilmList.filter { film -> film.isLiked }

    override fun getPopFilms(): List<FilmFeedModel> = popFilmList

    override fun getFavFilms(): List<FilmFeedModel> = favFilmList

    override fun saveFilms(list: List<FilmFeedModel>) {
        cache.addAll(list)
    }
}