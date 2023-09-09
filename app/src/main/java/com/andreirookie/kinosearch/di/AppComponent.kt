package com.andreirookie.kinosearch.di

import android.content.Context
import com.andreirookie.kinosearch.data.InMemoryRepository
import com.andreirookie.kinosearch.data.InMemoryRepositoryImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [InMemoryRepositoryModule::class])
interface AppComponent {

    fun provideInMemoryRepository(): InMemoryRepository

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Module
interface InMemoryRepositoryModule {

    @Singleton
    @Binds
    fun bindRepository(impl: InMemoryRepositoryImpl): InMemoryRepository
}