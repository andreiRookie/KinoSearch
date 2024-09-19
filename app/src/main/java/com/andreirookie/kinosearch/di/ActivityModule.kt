package com.andreirookie.kinosearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetFilmInfoUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
import com.andreirookie.kinosearch.domain.usecase.SearchUseCase
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
        getFavFilmsUseCase: GetFavFilmsUseCase,
        likeFilmUseCase: LikeFilmUseCase
    ): FavFragViewModelFactory {
        return FavFragViewModelFactory(getFavFilmsUseCase,likeFilmUseCase)
    }

    @ActivityScope
    @Provides
    fun providePopFragViewModelFactory(
        searchUseCase: SearchUseCase,
        getPopFilmsUseCase: GetPopFilmsUseCase,
        getPopFilmsByPageUseCase: GetPopFilmsByPageUseCase,
        likeFilmUseCase: LikeFilmUseCase
    ): PopFragViewModelFactory {
        return PopFragViewModelFactory(
            searchUseCase,
            getPopFilmsUseCase,
            getPopFilmsByPageUseCase,
            likeFilmUseCase
        )
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
    private val searchUseCase: SearchUseCase,
    private val getPopFilmsUseCase: GetPopFilmsUseCase,
    private val getPopFilmsByPageUseCase: GetPopFilmsByPageUseCase,
    private val likeFilmUseCase: LikeFilmUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            PopFragViewModel::class.java -> {
                PopFragViewModel(
                    searchUseCase,
                    getPopFilmsUseCase,
                    getPopFilmsByPageUseCase,
                    likeFilmUseCase
                ) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FavFragViewModelFactory @Inject constructor(
    private val getFavFilmsUseCase: GetFavFilmsUseCase,
    private val likeFilmUseCase: LikeFilmUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FavFragViewModel::class.java -> {
                FavFragViewModel(getFavFilmsUseCase, likeFilmUseCase) as T
            }
            else -> {
                error("Unknown $modelClass")
            }
        }
    }
}