package com.cutetech.memorymatchup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BackgroundGradient(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF061738),
                        Color(0xFF0E1353),
                        Color(0xFF061738),
                    )
                )
            )
    ) {
        content()
    }
}