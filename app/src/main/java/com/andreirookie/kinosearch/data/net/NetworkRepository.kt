package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.mapper.Mapper
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.domain.FilmDetailsModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.Staff
import kotlinx.coroutines.delay
import javax.inject.Inject

interface NetworkRepository {
    suspend fun loadPopularFilmsByPage(page: Int): List<FilmFeedModel>
    suspend fun loadFilmById(filmId: Int): FilmDetailsModel
    suspend fun loadStaffByFilmId(id: Int): List<Staff>
    suspend fun searchFilmByKeyword(keyword: String): List<FilmFeedModel>
}

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val mapperFilms: Mapper<FilmNetModel, FilmFeedModel>,
    private val mapperFilmDetailsFeedModel: Mapper<FilmDetailsNetModel, FilmDetailsModel>,
    private val mapperFilmStaff: Mapper<StaffNetModel, Staff>,
    private val dbRepository: DbRepository
) : NetworkRepository {

    override suspend fun loadPopularFilmsByPage(page: Int): List<FilmFeedModel> {
        val list = service.getTopFilmsByPages(page).let { response ->
            mapperFilms.mapFromEntityList(response.films)
        }
        dbRepository.insertAll(list)
        return list
    }

    override suspend fun loadFilmById(filmId: Int): FilmDetailsModel {
        return service.getFilmById(filmId).let {
            mapperFilmDetailsFeedModel.mapFromEntity(it)
        }
    }

    override suspend fun loadStaffByFilmId(id: Int): List<Staff> {
        return service.getStaffByFilmId(id).let { list ->
            mapperFilmStaff.mapFromEntityList(list)
        }
    }

    override suspend fun searchFilmByKeyword(keyword: String): List<FilmFeedModel> {
        return service.searchByKeyword(keyword).let { response ->
            mapperFilms.mapFromEntityList(response.films)
        }
    }
}