package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsByPageUseCase
import com.andreirookie.kinosearch.domain.usecase.GetPopFilmsUseCase
import com.andreirookie.kinosearch.domain.usecase.SearchState
import com.andreirookie.kinosearch.domain.usecase.SearchUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class PopFragViewModel(
    private val dbRepository: DbRepository,
    private val searchUseCase: SearchUseCase,
    private val getPopFilmsUseCase: GetPopFilmsUseCase,
    private val getPopFilmsByPageUseCase: GetPopFilmsByPageUseCase
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _searchPublisherSubject = PublishSubject.create<String>()
    val searchObserver: Observer<String> get() = _searchPublisherSubject

    private val _feedState =
        MutableStateFlow<FilmFeedState<List<FilmFeedModel>>>(FilmFeedState.Init())
    val feedState: StateFlow<FilmFeedState<List<FilmFeedModel>>> get() = _feedState.asStateFlow()

    private val searchQueryFlow: MutableSharedFlow<String> = MutableSharedFlow()

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
                     .onErrorReturn { SearchState.Result(emptyList()) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { searchState ->
                viewModelScope.launch {
                    _searchStateFlow.emit(searchState)
                }
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
        viewModelScope.launch {
            try {
                dbRepository.likeFilm(film)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
//        viewModelScope.coroutineContext.cancelChildren()
        viewModelJob.cancelChildren()
    }
}