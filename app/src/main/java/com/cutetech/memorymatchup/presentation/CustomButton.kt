package com.cutetech.memorymatchup.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutetech.memorymatchup.ui.theme.AccentBlue
import com.cutetech.memorymatchup.ui.theme.museoFontFamily
import kotlin.math.abs

@Composable
fun SpringButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val offsetPadding by animateDpAsState(targetValue = if (isPressed) 0.dp else 2.dp)

    var buttonSize by remember { mutableStateOf(IntSize(0, 0)) }
    val shadowHeightDp = with(LocalDensity.current) {
        buttonSize.height.toDp()
    }

    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(key1 = isPressed) {
        if (isPressed) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(shadowHeightDp)
                .padding(
                    top = 2.dp,
                    start = 2.dp
                )
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .align(Alignment.BottomEnd)
        )

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            shape = RoundedCornerShape(8.dp),
            interactionSource = interactionSource,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = abs(offsetPadding.minus(2.dp).value).dp,
                    top = abs(offsetPadding.minus(2.dp).value).dp,
                    bottom = offsetPadding,
                    end = offsetPadding,
                )
                .fillMaxWidth()
                .onGloballyPositioned { buttonSize = it.size }
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = museoFontFamily,
                    fontSize = 24.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun SpringButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        SpringButton(
            text = "PLAY",
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {}
    }
}