package com.cutetech.memorymatchup.presentation.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    BackgroundGradient {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val isPaused by remember { mutableStateOf(true) }

            Column(Modifier.fillMaxSize()) {

            }

            if (isPaused) {
                PauseBox(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    onResume = {},
                    onExit = {}
                )
            }
        }
    }
}
/*
var cardFace by remember { mutableStateOf(CardFace.Front) }
val tileState = remember {
    TileState(
        isRevealed = false,
        tile = Tile("dragon", drawable.dragon)
    )
}

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
)*/