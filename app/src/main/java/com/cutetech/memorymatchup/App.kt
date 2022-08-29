package com.cutetech.memorymatchup

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.cutetech.memorymatchup.domain.utilities.GameMediaPlayer
import com.cutetech.memorymatchup.utils.Prefs
import com.cutetech.memorymatchup.utils.PrefsConstants
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), LifecycleEventObserver {

    @Inject
    lateinit var gameMediaPlayer: GameMediaPlayer

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Prefs.init(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        if (!Prefs.contains(PrefsConstants.IS_SOUND_ON)) {
            Prefs[PrefsConstants.IS_SOUND_ON] = true
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                gameMediaPlayer.pauseGlobalMusic()
            }

            Lifecycle.Event.ON_START -> {
                gameMediaPlayer.playGlobalMusic()
            }

            Lifecycle.Event.ON_DESTROY -> {
                gameMediaPlayer.stopGlobalMusic()
            }

            else -> Unit
        }
    }
}