package com.andreirookie.kinosearch.di

import android.content.Context
import com.andreirookie.kinosearch.data.db.AppDatabase
import com.andreirookie.kinosearch.data.db.FilmDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
    @Singleton
    @Provides
    fun provideDao(appDataBase: AppDatabase): FilmDao {
        return appDataBase.dao()
    }
}