package com.cutetech.memorymatchup.domain

import com.cutetech.memorymatchup.utils.timer.TimerStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimerOrchestrator @Inject constructor(
    private val timerStateHolder: TimerStateHolder,
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val _ticker = MutableStateFlow("")
    val ticker = _ticker.asStateFlow()

    fun start() {
        if (job == null) startJob()
        timerStateHolder.start()
    }

    private fun startJob() {
        scope.launch {
            while (isActive) {
                _ticker.value = timerStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    fun pause() {
        timerStateHolder.pause()
        stopJob()
    }

    fun stop() {
        timerStateHolder.stop()
        stopJob()
//        clearValue()
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        _ticker.value = ""
    }
}