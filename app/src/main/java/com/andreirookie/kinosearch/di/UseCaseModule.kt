package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCaseImpl
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindGetPopFilmsByPageUseCase(impl: GetPopFilmsByPageUseCaseImpl): GetPopFilmsByPageUseCase

    @Binds
    fun bindGetPopFilmsUseCase(impl: GetPopFilmsUseCaseImpl): GetPopFilmsUseCase
}