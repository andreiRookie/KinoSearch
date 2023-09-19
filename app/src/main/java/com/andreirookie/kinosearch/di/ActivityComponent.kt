package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.MainActivity
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import dagger.Component
import javax.inject.Scope


@Scope
//@Retention(AnnotationRetention.RUNTIME) TODO ask about
annotation class ActivityScope

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun provideInMemoryRepository(): InMemoryRepository
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