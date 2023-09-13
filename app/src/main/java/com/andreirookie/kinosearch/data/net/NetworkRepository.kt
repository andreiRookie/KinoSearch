package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.models.Film
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.fragments.feed.FilmMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NetworkRepository {
    suspend fun loadPopularFilms(): List<Film>
    suspend fun loadFilmById(filmId: Int): Film
}

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: FilmMapper<FilmNetModel, Film>,
    private val mapperDetails: FilmMapper<FilmDetailsNetModel, Film>,
) : NetworkRepository {

    override suspend fun loadPopularFilms(): List<Film> {
        return withContext(dispatcher) {
            service.getTopFilms().let {
                mapper.mapFromEntityList(it.films)
            }
        }
    }

    override suspend fun loadFilmById(filmId: Int): Film {
        return withContext(dispatcher) {
            service.getFilmById(filmId).let {
                mapperDetails.mapFromEntity(it)
            }
        }
    }
}

interface ApiCallback<T> { // Todo delete
    fun onResponse(response: T)
    fun onFailure(e: Exception)
}