package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.BuildConfig
import com.andreirookie.kinosearch.data.models.FilmApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {
    @Headers("X-API-KEY: ${BuildConfig.API_KEY}")
    @GET(value = "/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(): FilmApiResponse
}