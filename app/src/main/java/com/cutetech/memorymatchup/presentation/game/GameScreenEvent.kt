package com.cutetech.memorymatchup.presentation.game

sealed class GameScreenEvent {
    data class PauseStateChanged(val isPaused: Boolean) : GameScreenEvent()
    data class TileFlipped(val tileState: TileState, val position: Int) : GameScreenEvent()
    object GameEnded : GameScreenEvent()
}
