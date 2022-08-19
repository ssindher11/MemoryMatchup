package com.cutetech.memorymatchup.domain.utilities

import javax.inject.Inject

class TimestampFormatter @Inject constructor() {
    fun format(timestamp: Long): String {
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes / 60).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted"
        }
    }

    private fun Long.pad(length: Int) = this.toString().padStart(length, '0')
}