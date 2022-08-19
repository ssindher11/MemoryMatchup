package com.cutetech.memorymatchup.utils.timer

import com.cutetech.memorymatchup.domain.model.TimerState
import com.cutetech.memorymatchup.domain.repository.TimestampProvider
import com.cutetech.memorymatchup.domain.utilities.ElapsedTimeCalculator
import javax.inject.Inject

class TimerStateCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) {
    fun calculateRunningState(oldState: TimerState): TimerState.Running =
        when (oldState) {
            is TimerState.Running -> oldState
            is TimerState.Paused -> TimerState.Running(
                startTime = timestampProvider.getMilliseconds(),
                elapsedTime = oldState.elapsedTime
            )
        }

    fun calculatePausedState(oldState: TimerState): TimerState.Paused =
        when (oldState) {
            is TimerState.Paused -> oldState
            is TimerState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                TimerState.Paused(elapsedTime = elapsedTime)
            }
        }
}