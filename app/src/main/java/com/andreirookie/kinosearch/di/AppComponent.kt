package com.andreirookie.kinosearch.di

import android.content.Context
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
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

    fun provideLikeFilmUseCase(): LikeFilmUseCase
    fun provideGetFavFilmsUseCase(): GetFavFilmsUseCase
    fun provideGetPopFilmsByPageUseCase(): GetPopFilmsByPageUseCase
    fun provideGetPopFilmsUseCase(): GetPopFilmsUseCase
    fun provideNetworkRepository(): NetworkRepository
    fun provideDbRepository(): DbRepository

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}