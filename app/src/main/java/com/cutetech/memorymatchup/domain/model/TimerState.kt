package com.cutetech.memorymatchup.domain.model

sealed class TimerState {
    data class Paused(val elapsedTime: Long) : TimerState()
    data class Running(val startTime: Long, val elapsedTime: Long) : TimerState()
}