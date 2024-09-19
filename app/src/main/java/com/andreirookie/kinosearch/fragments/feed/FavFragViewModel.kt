package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.usecase.GetFavFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavFragViewModel(
    private val getFavFilmsUseCase: GetFavFilmsUseCase,
    private val likeFilmUseCase: LikeFilmUseCase
) : ViewModel() {

    private val _feedState =
        MutableStateFlow<FilmFeedState<List<FilmFeedModel>>>(FilmFeedState.Init())
    val feedState: StateFlow<FilmFeedState<List<FilmFeedModel>>> get() = _feedState.asStateFlow()

    private val compositeDisposable = CompositeDisposable()

    fun getFav() {
        getFavFilmsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _feedState.value = FilmFeedState.Loading() }
            .subscribe(
                { list ->
                    _feedState.value = FilmFeedState.Data(list)
                },
                { t ->
                    _feedState.value = FilmFeedState.Error(t)
                }
            )
            .addTo(compositeDisposable)
    }

    fun like(film: FilmFeedModel) {
        likeFilmUseCase.invoke(film)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}