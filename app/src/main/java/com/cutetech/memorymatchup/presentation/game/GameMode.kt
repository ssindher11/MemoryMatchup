package com.cutetech.memorymatchup.presentation.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameMode(val numberOfTiles: Int) : Parcelable {
    EASY(16),
    MEDIUM(20),
    HARD(36),
}