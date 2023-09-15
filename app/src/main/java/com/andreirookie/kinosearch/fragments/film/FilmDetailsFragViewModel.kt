package com.andreirookie.kinosearch.fragments.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreirookie.kinosearch.domain.GetFilmInfoUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsFragViewModel @Inject constructor(
    private val getFilmInfoUseCase: GetFilmInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FilmDetailsFragState>(FilmDetailsFragState.Init)
    val state: StateFlow<FilmDetailsFragState> get() = _state

    private val vmJob = SupervisorJob()
    private val vmScope = CoroutineScope(vmJob + Dispatchers.Main.immediate)

    fun loadFilmInfo(id: Int) {
        _state.value = FilmDetailsFragState.Loading
        vmScope.launch {
            try {
                val filmInfo = getFilmInfoUseCase.execute(id)
                _state.value = FilmDetailsFragState.Data(filmInfo)
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
        fun Factory(getFilmInfoUseCase: GetFilmInfoUseCase) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    FilmDetailsFragViewModel::class.java -> {
                        FilmDetailsFragViewModel(getFilmInfoUseCase) as T
                    }
                    else -> {
                        error("Unknown $modelClass")
                    }
                }
            }
        }
    }
}