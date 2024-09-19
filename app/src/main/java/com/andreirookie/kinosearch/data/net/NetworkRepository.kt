package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.mapper.Mapper
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.domain.FilmDetailsModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.Staff
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val API_REQUEST_INTERVAL = 200L

interface NetworkRepository {
    fun loadPopularFilmsByPage(page: Int): Observable<List<FilmFeedModel>>
    suspend fun loadFilmById(filmId: Int): FilmDetailsModel
    suspend fun loadStaffByFilmId(id: Int): List<Staff>
    fun searchFilmByKeyword(keyword: String): Single<List<FilmFeedModel>>
}

class NetworkRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val mapperFilms: Mapper<FilmNetModel, FilmFeedModel>,
    private val mapperFilmDetailsFeedModel: Mapper<FilmDetailsNetModel, FilmDetailsModel>,
    private val mapperFilmStaff: Mapper<StaffNetModel, Staff>,
    private val dbRepository: DbRepository
) : NetworkRepository {

    override fun loadPopularFilmsByPage(page: Int): Observable<List<FilmFeedModel>> {
        return service.getTopFilmsByPages(page)
            .subscribeOn(Schedulers.io())
            .delay(API_REQUEST_INTERVAL, TimeUnit.MILLISECONDS)
            .map { response -> mapperFilms.mapFromEntityList(response.films) }
            .observeOn(Schedulers.io())
            .doOnNext { list -> dbRepository.insertAll(list) }
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

    override fun searchFilmByKeyword(keyword: String): Single<List<FilmFeedModel>> {
        return service.searchByKeyword(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { response -> mapperFilms.mapFromEntityList(response.films) }
    }
}