package com.cutetech.memorymatchup.presentation.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.presentation.BackgroundGradient

@Composable
fun GameScreen() {
    var cardFace by remember { mutableStateOf(CardFace.Front) }
    val tileState = remember {
        TileState(
            isRevealed = false,
            tile = Tile("dragon", drawable.dragon)
        )
    }

    BackgroundGradient {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImageTile(
                cardFace = cardFace,
                onClick = { cardFace = cardFace.next },
                modifier = Modifier.size(100.dp),
                front = {
                    FrontFace()
                },
                back = {
                    BackFace(tileState = tileState)
                }
            )
        }
    }

}