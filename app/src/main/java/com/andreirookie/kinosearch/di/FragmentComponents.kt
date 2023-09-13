package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.fragments.feed.FavoriteFilmsFragment
import com.andreirookie.kinosearch.fragments.feed.FeedFragment
import com.andreirookie.kinosearch.fragments.feed.PopularFilmsFragment
import com.andreirookie.kinosearch.fragments.film.FilmDetailsFragment
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
interface FavoriteFilmsFragComponent {
    fun inject(fragment: FavoriteFilmsFragment)

    @Component.Factory
    interface FavoriteFilmsFragComponentFactory {
        fun create(activityComponent: ActivityComponent): FavoriteFilmsFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): FavoriteFilmsFragComponent {
            return DaggerFavoriteFilmsFragComponent.factory().create(activityComponent)
        }
    }
}

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface PopularFilmsFragComponent {
    fun inject(fragment: PopularFilmsFragment)

    @Component.Factory
    interface PopularFilmsFragComponentFactory {
        fun create(activityComponent: ActivityComponent): PopularFilmsFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): PopularFilmsFragComponent {
            return DaggerPopularFilmsFragComponent.factory().create(activityComponent)
        }
    }
}

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface FilmDetailsFragComponent {
    fun inject(fragment: FilmDetailsFragment)

    @Component.Factory
    interface FilmDetailsFragComponentFactory {
        fun create(activityComponent: ActivityComponent): FilmDetailsFragComponent
    }

    companion object {
        fun getComponent(activityComponent: ActivityComponent): FilmDetailsFragComponent {
            return DaggerFilmDetailsFragComponent.factory().create(activityComponent)
        }
    }
}

@Scope
annotation class FragmentScope