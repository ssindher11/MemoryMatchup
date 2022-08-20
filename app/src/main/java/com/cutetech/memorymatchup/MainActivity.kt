package com.cutetech.memorymatchup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.presentation.NavGraphs
import com.cutetech.memorymatchup.ui.theme.MemoryMatchupTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryMatchupTheme {
                BackgroundGradient {
                    DestinationsNavHost(navGraph = NavGraphs.root, engine = rememberAnimatedNavHostEngine())
                }
            }
        }
    }
}