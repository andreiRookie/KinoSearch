package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.mapper.Mapper
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.domain.FilmDetailsModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.Staff
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NetworkRepository {
    suspend fun loadPopularFilms(): List<FilmFeedModel>
    suspend fun loadFilmById(filmId: Int): FilmDetailsModel
    suspend fun loadStaffByFilmId(id: Int): List<Staff>
    suspend fun searchFilmByKeyword(keyword: String): List<FilmFeedModel>
}

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dispatcherIo: CoroutineDispatcher,
    private val mapperFilms: Mapper<FilmNetModel, FilmFeedModel>,
    private val mapperFilmDetailsFeedModel: Mapper<FilmDetailsNetModel, FilmDetailsModel>,
    private val mapperFilmStaff: Mapper<StaffNetModel, Staff>
) : NetworkRepository {

    override suspend fun loadPopularFilms(): List<FilmFeedModel> {
        return withContext(dispatcherIo) {
            service.getTopFilms().let { response ->
                mapperFilms.mapFromEntityList(response.films)
            }
        }
    }

    override suspend fun loadFilmById(filmId: Int): FilmDetailsModel {
        return withContext(dispatcherIo) {
            service.getFilmById(filmId).let {
                mapperFilmDetailsFeedModel.mapFromEntity(it)
            }
        }
    }

    override suspend fun loadStaffByFilmId(id: Int): List<Staff> {
        return withContext(dispatcherIo) {
            service.getStaffByFilmId(id).let { list ->
                mapperFilmStaff.mapFromEntityList(list)
            }
        }
    }

    override suspend fun searchFilmByKeyword(keyword: String): List<FilmFeedModel> {
        return withContext(dispatcherIo) {
            service.searchByKeyword(keyword).let { response ->
                mapperFilms.mapFromEntityList(response.films)
            }
        }
    }
}