package com.cutetech.memorymatchup.presentation.game

import nl.dionsegijn.konfetti.core.Party

sealed class ConfettiState {
    data class Started(val party: List<Party>) : ConfettiState()
    object Idle : ConfettiState()
}