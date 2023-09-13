package com.andreirookie.kinosearch.fragments.feed

import com.andreirookie.kinosearch.data.models.Film
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import javax.inject.Inject

interface FilmMapper<E, M> {
    fun mapFromEntity(e: E): M
    fun mapFromEntityList(list: List<E>): List<M>
}

class FilmMapperImpl @Inject constructor() : FilmMapper<FilmNetModel, Film> {

    override fun mapFromEntity(e: FilmNetModel): Film {
        return Film(
            id = e.kinopoiskId,
            title = e.nameRu ?: e.nameEn ?: "No title info",
            posterUrlPreview = e.posterUrlPreview,
            issueYear = e.year?.toString() ?: "",
            genre = e.genres.first().genre.setFirstCharInUpperCase(),
            country = e.countries.first().country.setFirstCharInUpperCase()
        )
    }

    override fun mapFromEntityList(list: List<FilmNetModel>): List<Film> {
        return list.map { entity -> mapFromEntity(entity) }
    }
}

class FilmDetailsMapperImpl @Inject constructor() : FilmMapper<FilmDetailsNetModel, Film> {

    override fun mapFromEntity(e: FilmDetailsNetModel): Film {
        return Film(
            id = e.kinopoiskId,
            title = e.nameRu ?: e.nameEn ?: "No title info",
            posterUrl = e.posterUrl,
            issueYear = e.year?.toString() ?: "",
            genre = e.genres.first().genre.setFirstCharInUpperCase(),
            country = e.countries.first().country.setFirstCharInUpperCase(),
            description = e.description ?: "",
            filmLength = e.filmLength?.let { "$it мин." } ?: ""
        )
    }

    override fun mapFromEntityList(list: List<FilmDetailsNetModel>): List<Film> {
        return list.map { entity -> mapFromEntity(entity) }
    }
}

private fun String.setFirstCharInUpperCase(): String {
    return this.replaceFirstChar { it.uppercase() }
}