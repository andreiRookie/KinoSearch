package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.fragments.feed.FavoriteMoviesFragment
import com.andreirookie.kinosearch.fragments.feed.FeedFragment
import com.andreirookie.kinosearch.fragments.feed.PopularMoviesFragment
import dagger.Component
import javax.inject.Scope

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface FeedFragComponent {
    fun inject(fragment: FeedFragment)

    @Component.Factory
    interface FeedFragComponentFactory {
        fun create(activityComponent: ActivityComponent): FeedFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): FeedFragComponent {
            return DaggerFeedFragComponent.factory().create(activityComponent)
        }
    }
}

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface FavoriteMoviesFragComponent {
    fun inject(fragment: FavoriteMoviesFragment)

    @Component.Factory
    interface FavoriteMoviesFragComponentFactory {
        fun create(activityComponent: ActivityComponent): FavoriteMoviesFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): FavoriteMoviesFragComponent {
            return DaggerFavoriteMoviesFragComponent.factory().create(activityComponent)
        }
    }
}

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface PopularMoviesFragComponent {
    fun inject(fragment: PopularMoviesFragment)

    @Component.Factory
    interface PopularMoviesFragComponentFactory {
        fun create(activityComponent: ActivityComponent): PopularMoviesFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): PopularMoviesFragComponent {
            return DaggerPopularMoviesFragComponent.factory().create(activityComponent)
        }
    }
}

@Scope
annotation class FragmentScope