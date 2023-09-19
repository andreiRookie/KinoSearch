package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.db.DbRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import com.andreirookie.kinosearch.domain.FilmFeedModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedFragViewModel(
    private val inMemoryRepo: InMemoryRepository,
    private val networkRepo: NetworkRepository,
    private val dbRepository: DbRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    private val _feedState = MutableStateFlow<FeedFragState>(FeedFragState.Init)
    val feedState: StateFlow<FeedFragState> get() = _feedState.asStateFlow()

    private val eventsChannel = Channel<ScreenEvent>(Channel.BUFFERED)
    val eventsFlow: Flow<ScreenEvent> = eventsChannel.receiveAsFlow()

    private val _popFilmsFlow = MutableStateFlow<List<FilmFeedModel>>(emptyList())
    val popFilmsFlow = _popFilmsFlow.asStateFlow()

    private val _favFilmsFlow = MutableStateFlow<List<FilmFeedModel>>(emptyList())
    val favFilmsFlow = _favFilmsFlow.asStateFlow()

    init {
        requestAll()
        refresh()
    }

    fun refresh() {
        getPop()
        getFav()
    }

    fun getPop() {
        _feedState.value = FeedFragState.Loading
        viewModelScope.launch {
            try {
                dbRepository.getPopFilms().collect { list ->
                    _popFilmsFlow.update { list }
                }
                _feedState.value = FeedFragState.Init
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FeedFragState.Error(e)

            }
        }
    }

    fun getFav() {
        _feedState.value = FeedFragState.Loading
        viewModelScope.launch {
            try {
                dbRepository.getFavFilms().collect { list ->
                    _favFilmsFlow.update { list }
                }
                _feedState.value = FeedFragState.Init
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FeedFragState.Error(e)

            }
        }
    }

    fun navigateToFilmDetailsFrag(id: Int) {
        viewModelScope.launch {
            eventsChannel.send(ScreenEvent.NavigateToFilmFragDetails(id))
        }
    }

    fun requestAll() {
        _feedState.value = FeedFragState.Loading
        viewModelScope.launch {
            try {
                dbRepository.requestAndSaveAll()
                _feedState.value = FeedFragState.Init
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FeedFragState.Error(e)
            }
        }
    }

    fun likeById(filmId: Int) {
        viewModelScope.launch {
            try {
                dbRepository.likeById(filmId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                eventsChannel.send(ScreenEvent.Error("Error"))
            }
        }
    }

//    fun loadPopFilms() {
//        _feedState.value = FeedFragState.Loading
//        viewModelScope.launch {
//            try {
//                    val films = dbRepository.queryPopFilms()
//                    if (films.isNotEmpty()) {
//                    _feedState.value = FeedFragState.PopularFilms(films)
//                } else {
//                    _feedState.value = FeedFragState.Init
//                }
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                eventsChannel.send(Event.Error(e))
//            }
//        }
//    }
//
//    fun loadFavFilms() {
//        _feedState.value = FeedFragState.Loading
//        viewModelScope.launch {
//            try {
//                val films = dbRepository.queryFavoriteFilms()
//                if (films.isNotEmpty()) {
//                    _feedState.emit(FeedFragState.FavoriteFilms(films))
//                } else {
//                    _feedState.value = FeedFragState.Init
//                }
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                eventsChannel.send(Event.Error(e))
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

