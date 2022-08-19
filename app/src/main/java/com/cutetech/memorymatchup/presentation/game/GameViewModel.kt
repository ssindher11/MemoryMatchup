package com.cutetech.memorymatchup.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutetech.memorymatchup.domain.TimerOrchestrator
import com.cutetech.memorymatchup.domain.repository.ContentRepository
import com.cutetech.memorymatchup.utils.KonfettiPresets
import com.cutetech.memorymatchup.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val timerOrchestrator: TimerOrchestrator,
) : ViewModel() {

    var state by mutableStateOf(GameScreenState())
    val timerValue = timerOrchestrator.ticker

    private val _confettiState = MutableStateFlow<ConfettiState>(ConfettiState.Idle)
    val confettiState = _confettiState.asStateFlow()

    init {
        timerOrchestrator.start()
    }

    fun onEvent(event: GameScreenEvent) {
        when (event) {
            GameScreenEvent.GameEnded -> {
                timerOrchestrator.stop()
                onEvent(GameScreenEvent.ConfettiStateChanged(toStart = true))

                val gameScore = calculateGameScore(
                    state.nFlips,
                    state.tilesStateList.size,
                )
                state = state.copy(isEnded = true, gameScore = gameScore)
            }

            is GameScreenEvent.PauseStateChanged -> {
                state = state.copy(isPaused = event.isPaused)
                if (event.isPaused) {
                    timerOrchestrator.pause()
                } else {
                    timerOrchestrator.start()
                }
            }

            is GameScreenEvent.TileFlipped -> {
                // Do nothing if tile is already flipped
                if (state.revealedTile?.position == event.position) {
                    return
                }
                // Increase flip count
                state = state.copy(nFlips = state.nFlips + 1)

                // Flip the tile
                val newTileValue = event.tileState.copy(cardFace = CardFace.Back)
                state.tilesStateList[event.position] = newTileValue

                // Match against flipped tile else set it
                if (state.revealedTile == null) {
                    // Set tile to open in state
                    state = state.copy(revealedTile = event.tileState)
                } else {
                    // Match and close / hide tiles
                    state.revealedTile?.let { revealedTile ->
                        if (revealedTile.tile.name == event.tileState.tile.name) {
                            hideTiles(revealedTile.position, event.position)
                            state = state.copy(matchedPairs = state.matchedPairs + 1)
                            if (state.matchedPairs == (state.tilesStateList.size / 2)) {
                                onEvent(GameScreenEvent.GameEnded)
                            }
                        } else {
                            closeTiles(revealedTile.position, event.position)
                        }
                    }
                }
            }

            is GameScreenEvent.ConfettiStateChanged -> {
                _confettiState.value = if (event.toStart) {
                    ConfettiState.Started(KonfettiPresets.rain())
                } else {
                    ConfettiState.Idle
                }
            }
        }
    }

    /**
     * Launch a coroutine to hide the tiles
     *
     * @param i1 position of first tile
     * @param i2 position of second tile
     */
    private fun hideTiles(i1: Int, i2: Int) {
        viewModelScope.launch {
            delay(500)
            val t1 = state.tilesStateList[i1].copy(isVisible = false)
            val t2 = state.tilesStateList[i2].copy(isVisible = false)

            state.tilesStateList[i1] = t1
            state.tilesStateList[i2] = t2
            state = state.copy(revealedTile = null)
        }
    }

    /**
     * Launch a coroutine to flip the tiles back
     *
     * @param i1 position of first tile
     * @param i2 position of second tile
     */
    private fun closeTiles(i1: Int, i2: Int) {
        viewModelScope.launch {
            delay(500)
            val t1 = state.tilesStateList[i1].copy(cardFace = CardFace.Front)
            val t2 = state.tilesStateList[i2].copy(cardFace = CardFace.Front)

            state.tilesStateList[i1] = t1
            state.tilesStateList[i2] = t2
            state = state.copy(revealedTile = null)
        }
    }

    fun getTilesForGame(gameMode: GameMode) {
        viewModelScope.launch {
            contentRepository.getTiles(gameMode.numberOfTiles).collect {
                when (it) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> state = state.copy(isLoading = it.isLoading)
                    is Resource.Success -> {
                        val tilesList = it.data?.mapIndexed { index, tile ->
                            TileState(tile = tile, position = index)
                        } ?: emptyList()
                        state = state.copy(tilesStateList = tilesList.toMutableList())
                    }
                }
            }
        }
    }

    /**
     * @param nFlips number of card flips
     * @param elapsedTime time in milliseconds
     * @return game score in (1 | 2 | 3)
     */
    private fun calculateGameScore(nFlips: Int, nTiles: Int): Int {
        val accuracy = nTiles * 100.0 / nFlips
        val nStars = when {
            accuracy <= 43 -> 1
            accuracy in 44.0..58.0 -> 2
            else -> 3
        }
        return nStars
    }
}

