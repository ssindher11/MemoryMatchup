package com.cutetech.memorymatchup.presentation.level

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme

class ChooseLevelActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryMatchupTheme {
                ChooseLevelScreen()
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(getLaunchIntent(context))
        }

        private fun getLaunchIntent(context: Context, vararg flags: Int): Intent {
            return with(Intent(context, ChooseLevelActivity::class.java)) {
                flags.forEach { addFlags(it) }
                this
            }
        }
    }
}