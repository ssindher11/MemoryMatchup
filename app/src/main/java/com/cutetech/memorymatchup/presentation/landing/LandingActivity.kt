package com.cutetech.memorymatchup.presentation.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cutetech.memorymatchup.domain.utilities.GameMediaPlayer
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme
import com.cutetech.memorymatchup.utils.Prefs
import com.cutetech.memorymatchup.utils.PrefsConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingActivity : ComponentActivity() {

    @Inject
    lateinit var gameMediaPlayer: GameMediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MemoryMatchupTheme {
                LandingScreen(
                    toggleSound = { turnOn ->
                        Prefs[PrefsConstants.IS_SOUND_ON] = turnOn
                        if (turnOn) {
                            gameMediaPlayer.playGlobalMusic()
                            gameMediaPlayer.playButtonClickSound()
                        } else {
                            gameMediaPlayer.pauseGlobalMusic()
                        }
                    }
                )
            }
        }
    }

    companion object {
        fun clearLaunch(context: Context) {
            val intent = getLaunchIntent(
                context,
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            context.startActivity(intent)
        }

        private fun getLaunchIntent(context: Context, vararg flags: Int): Intent {
            return with(Intent(context, LandingActivity::class.java)) {
                flags.forEach { addFlags(it) }
                this
            }
        }
    }
}