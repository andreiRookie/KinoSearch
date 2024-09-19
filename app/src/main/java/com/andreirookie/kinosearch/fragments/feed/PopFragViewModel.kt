package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.LikeFilmUseCase
import com.andreirookie.kinosearch.domain.usecase.SearchState
import com.andreirookie.kinosearch.domain.usecase.SearchUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit


class PopFragViewModel(
    private val searchUseCase: SearchUseCase,
    private val getPopFilmsUseCase: GetPopFilmsUseCase,
    private val getPopFilmsByPageUseCase: GetPopFilmsByPageUseCase,
    private val likeFilmUseCase: LikeFilmUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _searchPublisherSubject = PublishSubject.create<String>()
    val searchObserver: Observer<String> get() = _searchPublisherSubject

    private val _feedState =
        MutableStateFlow<FilmFeedState<List<FilmFeedModel>>>(FilmFeedState.Init())
    val feedState: StateFlow<FilmFeedState<List<FilmFeedModel>>> get() = _feedState.asStateFlow()

    private val _searchStateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Init)
    val searchStateFlow: StateFlow<SearchState> get() = _searchStateFlow.asStateFlow()

    init {
        getPopFilms()
        subscribeToSearchWithUseCase()
    }

    private fun subscribeToSearchWithUseCase() {
        _searchPublisherSubject
            .filter { it.isNotEmpty()  }
            .distinctUntilChanged()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .switchMapSingle { query ->
                      searchUseCase(query)
                          .map { result -> SearchState.Result(result.list) }
                     .onErrorReturnItem(SearchState.Result(emptyList()))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { searchState ->
                _searchStateFlow.value = searchState
            }
            .addTo(compositeDisposable)
    }

    fun getPopFilms() {
        getPopFilmsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _feedState.value = FilmFeedState.Loading() }
            .subscribeBy(
                onSuccess = { list -> _feedState.value = FilmFeedState.Data(list) },
                onError = { t -> _feedState.value = FilmFeedState.Error(t) }
            )
            .addTo(compositeDisposable)
    }

    fun requestMoreFilmsByPage(page: Int) {
        getPopFilmsByPageUseCase(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun like(film: FilmFeedModel) {
        likeFilmUseCase.invoke(film)
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}