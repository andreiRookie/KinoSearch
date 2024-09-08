package com.andreirookie.kinosearch.di

import android.content.Context
import androidx.room.Room
import com.andreirookie.kinosearch.data.db.AppDatabase
import com.andreirookie.kinosearch.data.db.FilmDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    private const val APP_DATABASE = "app_database.db"

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(appDataBase: AppDatabase): FilmDao {
        return appDataBase.dao()
    }
}