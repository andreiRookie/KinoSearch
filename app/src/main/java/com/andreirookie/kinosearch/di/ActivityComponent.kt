package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.MainActivity
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
import dagger.Component
import javax.inject.Scope


@Scope
annotation class ActivityScope

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun provideLikeFilmUseCase(): LikeFilmUseCase
    fun provideGetFavFilmsUseCase(): GetFavFilmsUseCase
    fun provideGetPopFilmsByPageUseCase(): GetPopFilmsByPageUseCase
    fun provideGetPopFilmsUseCase(): GetPopFilmsUseCase
    fun provideNetworkRepository(): NetworkRepository
    fun provideDbRepository(): DbRepository

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ActivityComponentFactory {
        fun create(appComponent: AppComponent): ActivityComponent
    }
}

object ActivityComponentHolder {
    private var activityComponent: ActivityComponent? = null

    fun getComponent(appComponent: AppComponent): ActivityComponent {
        return activityComponent ?: DaggerActivityComponent.factory().create(appComponent).also {
            activityComponent = it
        }
    }
}