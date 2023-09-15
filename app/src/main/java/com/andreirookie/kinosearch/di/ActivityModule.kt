package com.andreirookie.kinosearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.GetFilmInfoUseCase
import com.andreirookie.kinosearch.fragments.feed.FeedFragViewModel
import com.andreirookie.kinosearch.fragments.film.FilmDetailsFragViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
object ActivityModule {

    @ActivityScope
    @Provides
    fun provideFilmDetailsFragViewModel(
        getFilmInfoUseCase: GetFilmInfoUseCase
    ): FilmDetailsFragViewModel {
        return FilmDetailsFragViewModel.Factory(getFilmInfoUseCase)
            .create(FilmDetailsFragViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun provideFeedFragViewModelFactory(
        inMemoryRepo: InMemoryRepository,
        networkRepo: NetworkRepository
    ): FeedFragViewModelFactory {
        return FeedFragViewModelFactory(inMemoryRepo, networkRepo)
    }
}

@Suppress("UNCHECKED_CAST")
class FeedFragViewModelFactory @Inject constructor(
    private val inMemoryRepo: InMemoryRepository,
    private val networkRepo: NetworkRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FeedFragViewModel::class.java -> {
                FeedFragViewModel(inMemoryRepo, networkRepo) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}