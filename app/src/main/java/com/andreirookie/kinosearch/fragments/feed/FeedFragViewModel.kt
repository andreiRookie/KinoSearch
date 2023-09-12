package com.andreirookie.kinosearch.fragments.feed

import androidx.lifecycle.ViewModel
import com.andreirookie.kinosearch.data.cache.InMemoryRepository
import com.andreirookie.kinosearch.data.net.NetworkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedFragViewModel(
    private val inMemoryRepo: InMemoryRepository,
    private val networkRepo: NetworkRepository
) : ViewModel() {

    private val _feedState = MutableStateFlow<FeedFragState>(FeedFragState.Init)
    val feedState: StateFlow<FeedFragState> get() = _feedState

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)


    fun loadPopFilms() {
        _feedState.value =
            FeedFragState.Loading
        viewModelScope.launch {
            try {
                val films = networkRepo.loadPopularFilms()
                _feedState.value =
                    FeedFragState.PopularFilms(films)
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _feedState.value = FeedFragState.Error
            }
        }
    }

    fun getPopFilms() {
        viewModelScope.launch {
            _feedState.value = FeedFragState.PopularFilms(inMemoryRepo.getPopFilms())
        }
    }

    fun getFavFilms() {
        viewModelScope.launch {
            _feedState.value = FeedFragState.FavoriteFilms(inMemoryRepo.getFavFilms())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//    companion object {
//        fun Factory(inMemoryRepo: InMemoryRepository, networkRepo: NetworkRepository) = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return when (modelClass) {
//                    FeedFragViewModel::class.java -> {
//                        FeedFragViewModel(inMemoryRepo, networkRepo) as T
//                    } else -> {
//                        error("Unknown $modelClass")
//                    }
//                }
//            }
//        }
//    }
}

