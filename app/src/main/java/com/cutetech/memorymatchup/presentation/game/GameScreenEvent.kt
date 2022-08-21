package com.cutetech.memorymatchup.presentation.game

sealed class GameScreenEvent {
    data class PauseStateChanged(val isPaused: Boolean, val isLeaving: Boolean) : GameScreenEvent()
    data class TileFlipped(val tileState: TileState, val position: Int) : GameScreenEvent()
    object GameEnded : GameScreenEvent()
    data class NewGame(val gameMode: GameMode) : GameScreenEvent()
    data class ConfettiStateChanged(val toStart: Boolean) : GameScreenEvent()
}
