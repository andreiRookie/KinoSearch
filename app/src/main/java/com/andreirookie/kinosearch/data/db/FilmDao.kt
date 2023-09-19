package com.andreirookie.kinosearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(entities: List<FilmFeedEntity>)

    @Transaction
    @Query("SELECT * FROM films_feed ORDER BY film_id ASC")
    fun queryAll(): List<FilmFeedEntity>

    @Transaction
    @Query("SELECT * FROM films_feed WHERE is_liked = 1 ORDER BY film_id ASC")
    fun queryAllFavorites(): List<FilmFeedEntity>

    @Query("""UPDATE films_feed SET
        is_liked = CASE WHEN is_liked THEN 0 ELSE 1 END
        WHERE film_id = :filmId
    """)
    fun likeFilmById(filmId: Int)



//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertFilmDetailsInfoEntity(entity: FilmDetailsInfoEntity)

//    @Transaction
//    @Query("""SELECT""")
//    suspend fun queryFilmDetailsInfoEntity(filmId: Int): FilmDetailsInfoEntity
}