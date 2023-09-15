package com.andreirookie.kinosearch.data.mapper

import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.domain.Film
import javax.inject.Inject

class FilmMapperImpl @Inject constructor() : Mapper<FilmNetModel, Film> {

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

class FilmDetailsMapperImpl @Inject constructor() : Mapper<FilmDetailsNetModel, Film> {

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