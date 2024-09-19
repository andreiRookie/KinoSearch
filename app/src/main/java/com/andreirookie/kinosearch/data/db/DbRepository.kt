package com.andreirookie.kinosearch.data.db

import com.andreirookie.kinosearch.domain.FilmFeedModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface DbRepository {
    fun getPopFilms(): Observable<List<FilmFeedModel>>
    fun getFavFilms(): Single<List<FilmFeedModel>>
    fun likeFilm(film: FilmFeedModel): Completable
    fun insertAll(list: List<FilmFeedModel>)
}

class DbRepositoryImpl @Inject constructor(
    private val dao: FilmDao
) : DbRepository {
    override fun getPopFilms(): Observable<List<FilmFeedModel>> {
        return Observable.fromCallable { dao.queryAll().map { it.asModel() } }
            .subscribeOn(Schedulers.io())
    }

    override fun getFavFilms(): Single<List<FilmFeedModel>> {
        return Single.fromCallable { dao.queryAllFavorites().map { it.asModel() } }
            .subscribeOn(Schedulers.io())
    }

    override fun insertAll(list: List<FilmFeedModel>) {
        if (list.isNotEmpty()) {
            dao.insertAll(list.asEntityList())
        }
    }

    override fun likeFilm(film: FilmFeedModel): Completable {
        return Completable.fromAction {
            dao.insert(film.asEntity())
            dao.likeFilmById(film.id)
        }.subscribeOn(Schedulers.io())
    }

    private fun List<FilmFeedModel>.asEntityList(): List<FilmFeedEntity> {
        return this.map { it.asEntity() }
    }
}