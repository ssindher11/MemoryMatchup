package com.cutetech.memorymatchup.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.ui.theme.AccentBlue
import com.cutetech.memorymatchup.ui.theme.museoFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTile(
    tileState: TileState,
    modifier: Modifier = Modifier,
    onClick: (CardFace) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val rotation = animateFloatAsState(
        targetValue = tileState.cardFace.angle,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )


        Card(
            onClick = {
                onClick(tileState.cardFace)
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            },
            modifier = modifier
                .aspectRatio(1f)
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            AnimatedVisibility(visible = tileState.isVisible, exit = fadeOut()) {
            if (rotation.value <= 90f) {
                FrontFace()
            } else {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
                ) {
                    BackFace(tile = tileState.tile)
                }
            }
        }
    }
}


@Composable
fun FrontFace(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = AccentBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "M",
                color = Color.White,
                fontFamily = museoFontFamily,
                fontSize = 36.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun BackFace(
    tile: Tile,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = tile.imageRes),
                contentDescription = ""
            )
        }
    }
}

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

@Preview
@Composable
fun ImageTilePreview() {
    var tileState = TileState(
        tile = Tile("dragon", drawable.dragon),
        position = 0
    )
    BackgroundGradient {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageTile(
                onClick = { tileState = tileState.copy(cardFace = it.next) },
                modifier = Modifier.size(100.dp),
                tileState = tileState
            )
        }
    }
}