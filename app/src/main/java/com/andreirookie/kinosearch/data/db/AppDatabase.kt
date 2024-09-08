package com.andreirookie.kinosearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
    FilmFeedEntity::class,
    FilmDetailsInfoEntity::class
    ],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): FilmDao

}