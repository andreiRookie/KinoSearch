package com.andreirookie.kinosearch.di

import android.content.Context
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoryModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    UseCaseModule::class
])
interface AppComponent {

    fun provideGetPopFilmsByPageUseCase(): GetPopFilmsByPageUseCase
    fun provideGetPopFilmsUseCase(): GetPopFilmsUseCase
    fun provideInMemoryRepository(): InMemoryRepository
    fun provideNetworkRepository(): NetworkRepository
    fun provideDbRepository(): DbRepository

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}