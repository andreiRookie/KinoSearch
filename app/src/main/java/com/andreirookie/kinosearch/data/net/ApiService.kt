package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmsApiResponse
import com.andreirookie.kinosearch.data.models.StaffNetModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(value = "/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(): FilmsApiResponse

    @GET(value = "/api/v2.2/films/{id}")
    suspend fun getFilmById(@Path("id") filmId: Int): FilmDetailsNetModel

    @GET(value = "/api/v1/staff")
    suspend fun getStaffByFilmId(@Query("filmId") filmId: Int): List<StaffNetModel>

    @GET(value = "/api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(@Query("keyword") keyword: String): FilmsApiResponse
}