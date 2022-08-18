package com.cutetech.memorymatchup.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.domain.model.Tile
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.ui.theme.museoFontFamily
import com.cutetech.memorymatchup.utils.ResourceStateRender
import com.cutetech.memorymatchup.utils.isTablet

@Composable
fun GameScreen(
    gameMode: GameMode = GameMode.EASY
) {
    val gameViewModel: GameViewModel = viewModel()

    LaunchedEffect(gameMode) {
        gameViewModel.getTilesForGame(gameMode)
    }
    val tilesListState = gameViewModel.tilesState.collectAsState()
    val nColumns = if (isTablet()) 6 else 4

    BackgroundGradient {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(Modifier.fillMaxSize()) {
                AppBar(
                    onBackPress = { /*TODO*/ },
                    onPausePress = { gameViewModel.isGamePaused = true }
                )

                ResourceStateRender(
                    state = tilesListState,
                    onLoading = {
                        LoadingBox(Modifier.fillMaxSize(0.9f))
                    }
                ) { tilesList ->
                    Text(
                        text = "04:20",
                        fontFamily = museoFontFamily,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 36.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    )

                    Text(
                        text = "Flips: ${gameViewModel.flips}",
                        fontFamily = museoFontFamily,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(nColumns),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        items(tilesList.size) { index ->
                            var cardFace by remember { mutableStateOf(CardFace.Front) }
                            val tile = tilesList[index]
                            val tileState = remember {
                                TileState(
                                    isRevealed = false,
                                    tile = Tile(tile.name, tile.imageRes)
                                )
                            }

                            ImageTile(
                                cardFace = cardFace,
                                onClick = { cardFace = cardFace.next },
                                front = { FrontFace() },
                                back = { BackFace(tileState = tileState) }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = gameViewModel.isGamePaused) {
                PauseBox(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    onResume = {
                        gameViewModel.isGamePaused = false
                    },
                    onExit = {}
                )
            }
        }
    }
}

@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    onPausePress: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackPress,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White,
            )
        }

        IconButton(
            onClick = onPausePress,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = "Pause",
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun LoadingBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = string.loading),
            fontFamily = museoFontFamily,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 40.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}