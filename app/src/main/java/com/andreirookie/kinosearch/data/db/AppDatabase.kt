package com.andreirookie.kinosearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private const val APP_DATABASE = "app_database.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { builtDb ->  instance = builtDb }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                APP_DATABASE
            ).build()
        }
    }
}