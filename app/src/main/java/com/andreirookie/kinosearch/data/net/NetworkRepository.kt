package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.mapper.Mapper
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.domain.Film
import com.andreirookie.kinosearch.domain.Staff
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NetworkRepository {
    suspend fun loadPopularFilms(): List<Film>
    suspend fun loadFilmById(filmId: Int): Film
    suspend fun loadStaffByFilmId(id: Int): List<Staff>
}

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dispatcher: CoroutineDispatcher,
    private val mapperFilms: Mapper<FilmNetModel, Film>,
    private val mapperFilmDetails: Mapper<FilmDetailsNetModel, Film>,
    private val mapperFilmStaff: Mapper<StaffNetModel, Staff>
) : NetworkRepository {

    override suspend fun loadPopularFilms(): List<Film> {
        return withContext(dispatcher) {
            service.getTopFilms().let {
                mapperFilms.mapFromEntityList(it.films)
            }
        }
    }

    override suspend fun loadFilmById(filmId: Int): Film {
        return withContext(dispatcher) {
            service.getFilmById(filmId).let {
                mapperFilmDetails.mapFromEntity(it)
            }
        }
    }

    override suspend fun loadStaffByFilmId(id: Int): List<Staff> {
        return withContext(dispatcher) {
            service.getStaffByFilmId(id).let { list ->
                mapperFilmStaff.mapFromEntityList(list)
            }
        }
    }
}

interface ApiCallback<T> { // Todo delete
    fun onResponse(response: T)
    fun onFailure(e: Exception)
}