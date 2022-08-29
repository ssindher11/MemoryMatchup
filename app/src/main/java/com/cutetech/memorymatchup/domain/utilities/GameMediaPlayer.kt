package com.cutetech.memorymatchup.domain.utilities

import android.content.Context
import android.media.MediaPlayer
import com.cutetech.memorymatchup.R.raw
import com.cutetech.memorymatchup.utils.Prefs
import com.cutetech.memorymatchup.utils.PrefsConstants.IS_SOUND_ON
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameMediaPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val globalMediaPlayer by lazy {
        MediaPlayer.create(context, raw.mind_bender).apply {
            isLooping = true
            setVolume(0.4f, 0.4f)
        }
    }
    private var currentPosition = 0

    fun playGlobalMusic() {
        globalMediaPlayer.seekTo(currentPosition)
        globalMediaPlayer.startIfNotMuted()
    }

    fun pauseGlobalMusic() {
        globalMediaPlayer.pause()
        currentPosition = globalMediaPlayer.currentPosition
    }

    fun stopGlobalMusic() {
        globalMediaPlayer.stop()
        globalMediaPlayer.release()
    }

    fun playFlipSound() {
        MediaPlayer.create(context, raw.pop).startIfNotMuted()
    }

    fun playSuccessSound() {
        MediaPlayer.create(context, raw.success).startIfNotMuted()
    }

    fun playButtonClickSound() {
        MediaPlayer.create(context, raw.button_press).startIfNotMuted()
    }

    fun playPauseOpenSound() {
        MediaPlayer.create(context, raw.pause_open).apply {
            setVolume(0.15f, 0.15f)
            startIfNotMuted()
        }
    }

    fun playPauseCloseSound() {
        MediaPlayer.create(context, raw.pause_close).apply {
            setVolume(0.15f, 0.15f)
            startIfNotMuted()
        }
    }

    fun playLevelCompletedSound() {
        globalMediaPlayer.setVolume(0.1f, 0.1f)
        MediaPlayer.create(context, raw.level_completed).apply {
            setVolume(0.85f, 0.85f)
            startIfNotMuted()
            setOnCompletionListener {
                globalMediaPlayer.setVolume(0.4f, 0.4f)
            }
        }
    }

    private fun MediaPlayer.startIfNotMuted() {
        if (Prefs[IS_SOUND_ON]) start()
    }
}

