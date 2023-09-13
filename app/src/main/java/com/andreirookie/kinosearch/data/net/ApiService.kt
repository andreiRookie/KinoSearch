package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.PopularFilmsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(value = "/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(): PopularFilmsApiResponse

    @GET(value = "/api/v2.2/films/{id}")
    suspend fun getFilmById(@Path("id") filmId: Int): FilmDetailsNetModel
}