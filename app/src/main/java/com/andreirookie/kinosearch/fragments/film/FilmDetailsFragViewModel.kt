package com.andreirookie.kinosearch.fragments.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.data.net.NetworkRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class FilmDetailsFragViewModel @Inject constructor(
    private val networkRepo: NetworkRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FilmDetailsFragState>(FilmDetailsFragState.Init)
    val state: StateFlow<FilmDetailsFragState> get() = _state

    private val vmJob = SupervisorJob()
    private val vmScope = CoroutineScope(vmJob + Dispatchers.Main.immediate)

    fun loadFilmInfo(id: Int) {
        _state.value = FilmDetailsFragState.Loading
        vmScope.launch {
            try {
                val film = networkRepo.loadFilmById(id)
                _state.value = FilmDetailsFragState.Data(film)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = FilmDetailsFragState.Error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }

    companion object {
        fun Factory(networkRepo: NetworkRepository) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    FilmDetailsFragViewModel::class.java -> {
                        FilmDetailsFragViewModel(networkRepo) as T
                    }
                    else -> {
                        error("Unknown $modelClass")
                    }
                }
            }
        }
    }
}