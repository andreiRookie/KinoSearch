package com.andreirookie.kinosearch.data.mapper

import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.domain.FilmDetailsModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import javax.inject.Inject

class FilmMapperImpl @Inject constructor() : Mapper<FilmNetModel, FilmFeedModel> {

    override fun mapFromEntity(e: FilmNetModel): FilmFeedModel {
        return FilmFeedModel(
            id = e.kinopoiskId,
            title = e.nameRu ?: e.nameEn ?: "No title info",
            posterUrlPreview = e.posterUrlPreview,
            issueYear = e.year?.toString() ?: "",
            genre = e.genres.first().genre.setFirstCharInUpperCase()
        )
    }
    override fun mapFromEntityList(list: List<FilmNetModel>): List<FilmFeedModel> {
        return list.map { entity -> mapFromEntity(entity) }
    }
}

class FilmDetailsMapperImpl @Inject constructor() : Mapper<FilmDetailsNetModel, FilmDetailsModel> {

    override fun mapFromEntity(e: FilmDetailsNetModel): FilmDetailsModel {
        return FilmDetailsModel(
            id = e.kinopoiskId,
            title = e.nameRu ?: e.nameEn ?: "No title info",
            posterUrl = e.posterUrl,
            country = e.countries.first().country.setFirstCharInUpperCase(),
            description = e.description ?: "",
            filmLength = e.filmLength?.let { "$it мин." } ?: ""
        )
    }
    override fun mapFromEntityList(list: List<FilmDetailsNetModel>): List<FilmDetailsModel> {
        return list.map { entity -> mapFromEntity(entity) }
    }
}

private fun String.setFirstCharInUpperCase(): String {
    return this.replaceFirstChar { it.uppercase() }
}