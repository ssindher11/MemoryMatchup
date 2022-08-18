package com.cutetech.memorymatchup.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.domain.repository.ContentRepository
import com.cutetech.memorymatchup.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    var isGamePaused by mutableStateOf(false)
    var flips by mutableStateOf(5)

    private val _tilesState = MutableStateFlow<Resource<List<Tile>>>(Resource.Loading(false))
    val tilesState: StateFlow<Resource<List<Tile>>> = _tilesState

    fun getTilesForGame(gameMode: GameMode) {
        viewModelScope.launch {
            _tilesState.value = Resource.Loading(isLoading = true)
            contentRepository.getTiles(gameMode.numberOfTiles).collect {
                when (it) {
                    is Resource.Error -> _tilesState.value = Resource.Error(it.message)
                    is Resource.Loading -> _tilesState.value = Resource.Loading(it.isLoading)
                    is Resource.Success -> _tilesState.value = Resource.Success(it.data)
                }
            }
        }
    }
}

