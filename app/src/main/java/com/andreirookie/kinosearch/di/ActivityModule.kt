package com.andreirookie.kinosearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.GetFilmInfoUseCase
import com.andreirookie.kinosearch.domain.search.SearchUseCase
import com.andreirookie.kinosearch.fragments.feed.FavFragViewModel
import com.andreirookie.kinosearch.fragments.feed.PopFragViewModel
import com.andreirookie.kinosearch.fragments.film.FilmDetailsFragViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
object ActivityModule {

    @ActivityScope
    @Provides
    fun provideFavFragViewModelFactory(
        dbRepository: DbRepository
    ): FavFragViewModelFactory {
        return FavFragViewModelFactory(dbRepository)
    }

    @ActivityScope
    @Provides
    fun providePopFragViewModelFactory(
        dbRepository: DbRepository,
        searchUseCase: SearchUseCase
    ): PopFragViewModelFactory {
        return PopFragViewModelFactory(dbRepository, searchUseCase)
    }

    @ActivityScope
    @Provides
    fun provideFilmDetailsFragViewModel(
        getFilmInfoUseCase: GetFilmInfoUseCase
    ): FilmDetailsFragViewModel {
        return FilmDetailsFragViewModel.Factory(getFilmInfoUseCase)
            .create(FilmDetailsFragViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
class PopFragViewModelFactory @Inject constructor(
    private val dbRepository: DbRepository,
    private val searchUseCase: SearchUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            PopFragViewModel::class.java -> {
                PopFragViewModel(dbRepository, searchUseCase) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FavFragViewModelFactory @Inject constructor(
    private val dbRepository: DbRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FavFragViewModel::class.java -> {
                FavFragViewModel(dbRepository) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}