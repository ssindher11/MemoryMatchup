package com.cutetech.memorymatchup.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutetech.memorymatchup.domain.TimerOrchestrator
import com.cutetech.memorymatchup.domain.repository.ContentRepository
import com.cutetech.memorymatchup.domain.utilities.GameMediaPlayer
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
    private val gameMediaPlayer: GameMediaPlayer,
) : ViewModel() {

    var state by mutableStateOf(GameScreenState())
    val timerValue = timerOrchestrator.ticker

    private val _confettiState = MutableStateFlow<ConfettiState>(ConfettiState.Idle)
    val confettiState = _confettiState.asStateFlow()

    fun onEvent(event: GameScreenEvent) {
        when (event) {
            GameScreenEvent.GameEnded -> {
                timerOrchestrator.stop()
                onEvent(GameScreenEvent.ConfettiStateChanged(toStart = true))
                gameMediaPlayer.playLevelCompletedSound()

                val gameScore = calculateGameScore(
                    state.nFlips,
                    state.tilesStateList.size,
                )
                state = state.copy(isEnded = true, gameScore = gameScore)
            }

            is GameScreenEvent.NewGame -> {
                getTilesForGame(event.gameMode)
                state = GameScreenState()
            }

            is GameScreenEvent.PauseStateChanged -> {
                state = state.copy(isPaused = event.isPaused, isQuitting = event.isLeaving)
                if (event.isPaused) {
                    timerOrchestrator.pause()
                    gameMediaPlayer.playPauseOpenSound()
                } else {
                    timerOrchestrator.start()
                    gameMediaPlayer.playPauseCloseSound()
                }
            }

            is GameScreenEvent.TileFlipped -> {
                val tryingToFlipSameTile = state.revealedTilesMap.containsKey(event.position)
                val tryingToOpenMoreTiles = state.revealedTilesMap.size > 1
                val isGameNotReady =
                    state.isEnded || state.isPaused || state.isQuitting || state.isLoading
                if (tryingToFlipSameTile || tryingToOpenMoreTiles || isGameNotReady) {
                    return
                }

                // Increase flip count
                state = state.copy(nFlips = state.nFlips + 1)

                // Flip the tile
                val newTileValue = event.tileState.copy(cardFace = CardFace.Back)
                state.tilesStateList[event.position] = newTileValue
                gameMediaPlayer.playFlipSound()

                state.revealedTilesMap[event.position] = event.tileState

                if (state.revealedTilesMap.size == 2) {
                    val revealedTile = state.revealedTilesMap.values.first()
                    val currentTile = state.revealedTilesMap.values.last()
                    val areTilesMatching = revealedTile.tile.name == currentTile.tile.name
                    if (areTilesMatching) {
                        hideTiles(revealedTile.position, currentTile.position)
                        state = state.copy(matchedPairs = state.matchedPairs + 1)
                        val allPairsAreMatched = state.matchedPairs == state.tilesStateList.size / 2
                        if (allPairsAreMatched) {
                            onEvent(GameScreenEvent.GameEnded)
                        }
                    } else {
                        closeTiles(revealedTile.position, currentTile.position)
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

            gameMediaPlayer.playSuccessSound()
            state.tilesStateList[i1] = t1
            state.tilesStateList[i2] = t2
            state.revealedTilesMap.clear()
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
            state.revealedTilesMap.clear()
        }
    }

    private fun getTilesForGame(gameMode: GameMode) {
        timerOrchestrator.stop()
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
                        timerOrchestrator.start()
                    }
                }
            }
        }
    }

    /**
     * @param nFlips number of card flips
     * @param nTiles total number of cards
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

