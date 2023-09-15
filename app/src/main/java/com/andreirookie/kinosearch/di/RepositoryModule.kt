package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.cache.InMemoryRepositoryImpl
import com.andreirookie.kinosearch.data.mapper.FilmDetailsMapperImpl
import com.andreirookie.kinosearch.data.mapper.FilmMapperImpl
import com.andreirookie.kinosearch.data.mapper.FilmStaffMapper
import com.andreirookie.kinosearch.data.mapper.Mapper
import com.andreirookie.kinosearch.data.models.FilmDetailsNetModel
import com.andreirookie.kinosearch.data.models.FilmNetModel
import com.andreirookie.kinosearch.data.models.StaffNetModel
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.data.net.NetworkRepositoryImpl
import com.andreirookie.kinosearch.domain.Film
import com.andreirookie.kinosearch.domain.Staff
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
    fun bindFilmMapper(impl: FilmMapperImpl): Mapper<FilmNetModel, Film>

    @Binds
    fun bindFilmDetailsMapper(impl: FilmDetailsMapperImpl): Mapper<FilmDetailsNetModel, Film>

    @Binds
    fun bindFilmStaffMapper(impl: FilmStaffMapper): Mapper<StaffNetModel, Staff>
}