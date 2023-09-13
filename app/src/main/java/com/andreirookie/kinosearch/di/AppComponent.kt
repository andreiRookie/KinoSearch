package com.andreirookie.kinosearch.di

import android.content.Context
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, NetworkModule::class])
interface AppComponent {

    fun provideInMemoryRepository(): InMemoryRepository
    fun provideNetworkRepository(): NetworkRepository

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}