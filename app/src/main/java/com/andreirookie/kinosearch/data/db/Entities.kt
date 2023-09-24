package com.andreirookie.kinosearch.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreirookie.kinosearch.domain.FilmFeedModel

@Entity(tableName = "films_feed")
data class FilmFeedEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("film_id")
    val filmId: Int,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("poster_url_preview")
    val posterUrlPreview: String,

    @ColumnInfo("year")
    val issueYear: String,

    @ColumnInfo("genre")
    val genre: String,

    @ColumnInfo("is_liked")
    val isLiked: Boolean
) {
    fun asModel(): FilmFeedModel {
        return FilmFeedModel(
            id = filmId,
            title = title,
            posterUrlPreview = posterUrlPreview,
            issueYear = issueYear,
            genre = genre,
            isLiked = isLiked
        )
    }
}

@Entity(tableName = "film_info")
data class FilmDetailsInfoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("film_id")
    val filmId: Int,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("poster_url")
    val posterUrl: String = "",

    @ColumnInfo("country")
    val country: String,

    @ColumnInfo("film_length")
    val filmLength: String,

    @ColumnInfo("description")
    val description: String,
//    @Relation(parentColumn = "film_id", entityColumn = "id")
//    val film: FilmDetailsEntity,

    @ColumnInfo("director")
    val director: String,

    @ColumnInfo("actors")
    val actors: String
)


//@Entity(tableName = "films_details")
//data class FilmDetailsEntity(
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo("id")
//    val id: Int,
//
//    @ColumnInfo("title")
//    val title: String,
//
//    @ColumnInfo("poster_url")
//    val posterUrl: String = "",
//
//    @ColumnInfo("country")
//    val country: String,
//
//    @ColumnInfo("film_length")
//    val filmLength: String,
//
//    @ColumnInfo("description")
//    val description: String
//)


//@Entity(tableName = "films_staff")
//data class StaffEntity(
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo("staff_id")
//    val staffId: Int,
//    @ColumnInfo("film_id")
//    val filmId: Int,
//    @ColumnInfo("name")
//    val name: String,
//    @ColumnInfo("profession_key")
//    val professionKey: String
//)