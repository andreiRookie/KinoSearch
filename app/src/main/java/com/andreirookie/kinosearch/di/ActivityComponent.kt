package com.andreirookie.kinosearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.MainActivity
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.fragments.feed.FeedFragViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun provideInMemoryRepository(): InMemoryRepository
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

@Module
object ActivityModule {

    @ActivityScope
    @Provides
    fun provideFeedFragViewModelFactory(
        inMemoryRepository: InMemoryRepository
    ): FeedFragViewModelFactory {
        return FeedFragViewModelFactory(inMemoryRepository)
    }


}

@Suppress("UNCHECKED_CAST")
class FeedFragViewModelFactory @Inject constructor(
    private val inMemoryRepository: InMemoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FeedFragViewModel::class.java -> {
                FeedFragViewModel(inMemoryRepository) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}

@Scope
//@Retention(AnnotationRetention.RUNTIME) TODO ask about
annotation class ActivityScope