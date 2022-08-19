package com.cutetech.memorymatchup.domain.repository

import javax.inject.Inject

interface TimestampProvider {
    fun getMilliseconds(): Long
}

class TimestampProviderImpl @Inject constructor() : TimestampProvider {
    override fun getMilliseconds(): Long = System.currentTimeMillis()
}