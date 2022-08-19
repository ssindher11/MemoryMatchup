package com.cutetech.memorymatchup.di

import com.cutetech.memorymatchup.domain.TimerOrchestrator
import com.cutetech.memorymatchup.utils.timer.TimerStateHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TimerModule {

    companion object {
        @Provides
        @Singleton
        fun provideTimerOrchestrator(
            timerStateHolder: TimerStateHolder,
            @BackgroundDispatcherQualifier dispatcher: CoroutineDispatcher
        ): TimerOrchestrator {
            return TimerOrchestrator(
                timerStateHolder = timerStateHolder,
                scope = CoroutineScope(SupervisorJob() + dispatcher)
            )
        }
    }
}