package com.cutetech.memorymatchup.domain.utilities

import com.cutetech.memorymatchup.domain.model.TimerState
import com.cutetech.memorymatchup.domain.repository.TimestampProvider
import javax.inject.Inject

class ElapsedTimeCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider
) {
    fun calculate(state: TimerState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}