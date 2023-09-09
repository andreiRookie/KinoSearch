package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.data.InMemoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedFragViewModel(
    private val repository: InMemoryRepository
) : ViewModel() {

    private val _feedState = MutableStateFlow<FeedFragState>(FeedFragState.Init)
    val feedState: StateFlow<FeedFragState> get() = _feedState

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)

    fun getPopMovies() {
        viewModelScope.launch {
            _feedState.value = FeedFragState.TopMovies(repository.getPopMovies())
        }
    }

    fun getFavMovies() {
        viewModelScope.launch {
            _feedState.value = FeedFragState.FavoriteMovies(repository.getFavMovies())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//    companion object {
//        fun Factory(inMemoryRepository: InMemoryRepository) = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return when (modelClass) {
//                    FeedFragViewModel::class.java -> {
//                        FeedFragViewModel(inMemoryRepository) as T
//                    } else -> {
//                        error("Unknown $modelClass")
//                    }
//                }
//            }
//        }
//    }
}

