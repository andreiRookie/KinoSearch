package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCaseImpl
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCaseImpl
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCaseImpl
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindGetPopFilmsByPageUseCase(impl: GetPopFilmsByPageUseCaseImpl): GetPopFilmsByPageUseCase

    @Binds
    fun bindGetPopFilmsUseCase(impl: GetPopFilmsUseCaseImpl): GetPopFilmsUseCase

    @Binds
    fun bindGetFavFilmsUseCase(impl: GetFavFilmsUseCaseImpl): GetFavFilmsUseCase

    @Binds
    fun bindLikeFilmUseCase(impl: LikeFilmUseCaseImpl): LikeFilmUseCase
}