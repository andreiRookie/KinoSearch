package com.andreirookie.kinosearch.data.cache

import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface InMemoryRepository {
    fun getPopFilms(): Observable<List<FilmFeedModel>>
    fun getFavFilms(): List<FilmFeedModel>
    fun saveFilms(list: List<FilmFeedModel>)
}

class InMemoryRepositoryImpl @Inject constructor(
    private val cache: FilmsCache
) : InMemoryRepository {

    override  fun getPopFilms(): Observable<List<FilmFeedModel>> {
        return Observable.fromCallable { cache.getPopFilms() }
    }

    override fun getFavFilms(): List<FilmFeedModel> {
        return cache.getFavFilms()
    }

    override fun saveFilms(list: List<FilmFeedModel>) {
        cache.saveFilms(list)
    }
}