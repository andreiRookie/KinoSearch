package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.search.SearchState
import com.andreirookie.kinosearch.domain.search.SearchUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PopFragViewModel(
    private val dbRepository: DbRepository,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    private val _feedState = MutableStateFlow<FilmFeedState<List<FilmFeedModel>>>(FilmFeedState.Init())
    val feedState: StateFlow<FilmFeedState<List<FilmFeedModel>>> get() = _feedState.asStateFlow()

    private val searchQueryFlow: MutableSharedFlow<String> = MutableSharedFlow()

    private val _searchStateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Init)
    val searchStateFlow: StateFlow<SearchState> get() = _searchStateFlow.asStateFlow()

    init {
        subscribeToSearchFlow()
    }

    suspend fun search(query: String) {
        searchQueryFlow.emit(query)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearchFlow() {
        searchQueryFlow
            .filter { query -> query.isNotEmpty() }
            .distinctUntilChanged()
            .debounce(500L)
            .flatMapLatest { query -> flow { emit(searchWithUseCase(query)) } }
            .onEach { state -> _searchStateFlow.emit(state) }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private suspend fun searchWithUseCase(query: String): SearchState {
        val result = viewModelScope.async {
            try {
                val result = searchUseCase(query)
                if (result.list.isNotEmpty()) {
                    return@async SearchState.Result(result.list)
                } else {
                    return@async SearchState.Empty
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return@async SearchState.Error
            }
        }
        return result.await()
    }

    fun getPop() {
        _feedState.value = FilmFeedState.Loading()
        viewModelScope.launch {
            try {
                dbRepository.getPopFilms().collect { list ->
                    _feedState.value = FilmFeedState.Data(list)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FilmFeedState.Error(e)
            }
        }
    }

    fun requestAllByPage(page: Int) {
        _feedState.value = FilmFeedState.Loading()
        viewModelScope.launch {
            try {
                dbRepository.requestAndSaveAllByPage(page)

                dbRepository.getPopFilms().collect { list ->
                    _feedState.value = FilmFeedState.Data(list)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FilmFeedState.Error(e)
            }
        }
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
        viewModelScope.coroutineContext.cancelChildren()
    }
}