package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.data.db.DbRepository
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

class FavFragViewModel(
    private val dbRepository: DbRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    private val _feedState = MutableStateFlow<FeedFragState>(FeedFragState.Init)
    val feedState: StateFlow<FeedFragState> get() = _feedState.asStateFlow()

    private val eventsChannel = Channel<ScreenEvent>(Channel.BUFFERED)
    val eventsFlow: Flow<ScreenEvent> = eventsChannel.receiveAsFlow()

    private val _favFilmsFlow = MutableStateFlow<List<FilmFeedModel>>(emptyList())
    val favFilmsFlow = _favFilmsFlow.asStateFlow()

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

    fun like(film: FilmFeedModel) {
        viewModelScope.launch {
            try {
                dbRepository.likeFilm(film)

                dbRepository.getFavFilms().collect { list ->
                    _favFilmsFlow.update { list }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                eventsChannel.send(ScreenEvent.Error("Error"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}