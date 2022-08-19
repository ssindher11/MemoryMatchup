package com.cutetech.memorymatchup.utils.timer

import com.cutetech.memorymatchup.domain.model.TimerState
import com.cutetech.memorymatchup.domain.utilities.ElapsedTimeCalculator
import com.cutetech.memorymatchup.domain.utilities.TimestampFormatter
import javax.inject.Inject

class TimerStateHolder @Inject constructor(
    private val timerStateCalculator: TimerStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampFormatter: TimestampFormatter,
) {

    private var currentState: TimerState = TimerState.Paused(0)

    fun start() {
        currentState = timerStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = timerStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = TimerState.Paused(0)
    }

    fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is TimerState.Paused -> currentState.elapsedTime
            is TimerState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampFormatter.format(elapsedTime)
    }
}