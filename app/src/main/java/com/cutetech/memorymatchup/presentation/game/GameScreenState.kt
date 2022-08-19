package com.cutetech.memorymatchup.presentation.game

import com.cutetech.memorymatchup.domain.model.Tile

data class GameScreenState(
    val tilesStateList: MutableList<TileState> = mutableListOf(),
    val isLoading: Boolean = false,
    val isPaused: Boolean = false,
    val nFlips: Int = 0,
    val revealedTile: TileState? = null,
    val matchedPairs: Int = 0,
)

data class TileState(
    val position: Int,
    val tile: Tile,
    val isRevealed: Boolean = false,
    val isVisible: Boolean = true,
    val cardFace: CardFace = CardFace.Front,
)