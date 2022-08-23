package com.cutetech.memorymatchup.presentation.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MemoryMatchupTheme {
                LandingScreen()
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