package com.cutetech.memorymatchup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cutetech.memorymatchup.presentation.game.GameScreen
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryMatchupTheme {
                // A surface container using the 'background' color from the theme
//                LandingScreen()
                GameScreen()
            }
        }
    }
}