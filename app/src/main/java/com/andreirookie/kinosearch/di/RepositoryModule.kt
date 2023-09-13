package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.cache.InMemoryRepositoryImpl
import com.andreirookie.kinosearch.data.models.Film
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.data.net.NetworkRepositoryImpl
import com.andreirookie.kinosearch.fragments.feed.FilmDetailsMapperImpl
import com.andreirookie.kinosearch.fragments.feed.FilmMapper
import com.andreirookie.kinosearch.fragments.feed.FilmMapperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindInMemoryRepository(impl: InMemoryRepositoryImpl): InMemoryRepository

    @Singleton
    @Binds
    fun bindNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    fun provideMapper(impl: FilmMapperImpl): FilmMapper<FilmNetModel, Film>

    @Binds
    fun provideMapperDetails(impl: FilmDetailsMapperImpl): FilmMapper<FilmDetailsNetModel, Film>
}