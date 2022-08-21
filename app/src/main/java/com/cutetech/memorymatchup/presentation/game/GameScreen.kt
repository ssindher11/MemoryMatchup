package com.cutetech.memorymatchup.presentation.game

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.presentation.landing.LandingActivity
import com.cutetech.memorymatchup.ui.theme.museoFontFamily
import com.cutetech.memorymatchup.utils.LifecycleLaunchedEffect
import com.cutetech.memorymatchup.utils.isTablet
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@Composable
fun GameScreen(
    gameMode: GameMode = GameMode.EASY
) {
    val context = LocalContext.current
    val onBackPressDispatcher = LocalOnBackPressedDispatcherOwner.current

    val gameViewModel: GameViewModel = viewModel()

    LaunchedEffect(gameMode) {
        gameViewModel.onEvent(GameScreenEvent.NewGame(gameMode))
    }
    val timerValue by gameViewModel.timerValue.collectAsState()
    val screenState = gameViewModel.state
    val confettiState by gameViewModel.confettiState.collectAsState()

    LifecycleLaunchedEffect(keys = arrayOf(), lifecycleEvent = Lifecycle.Event.ON_PAUSE) {
        if (!screenState.isQuitting) {
            gameViewModel.onEvent(
                GameScreenEvent.PauseStateChanged(
                    isPaused = true,
                    isLeaving = false
                )
            )
        }
    }

    BackgroundGradient {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(Modifier.fillMaxSize()) {
                AppBar(
                    areIconsVisible = !screenState.isPaused,
                    onBackPress = { onBackPressDispatcher?.onBackPressedDispatcher?.onBackPressed() },
                    onPausePress = {
                        gameViewModel.onEvent(
                            GameScreenEvent.PauseStateChanged(
                                isPaused = true,
                                isLeaving = false
                            )
                        )
                    }
                )

                if (screenState.isLoading) {
                    LoadingBox(Modifier.fillMaxSize(0.9f))
                } else {
                    TilesGrid(
                        screenState = screenState,
                        gameMode = gameMode,
                        timerValue = timerValue,
                    ) {
                        gameViewModel.onEvent(it)
                    }
                }
            }

            ConfettiBox(confettiState = confettiState, modifier = Modifier.fillMaxSize()) {
                gameViewModel.onEvent(GameScreenEvent.ConfettiStateChanged(toStart = false))
            }

            AnimatedVisibility(visible = screenState.isEnded) {
                GameOverBox(
                    gameScore = screenState.gameScore,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    onNewGame = {
                        gameViewModel.onEvent(GameScreenEvent.NewGame(gameMode))
                    },
                    onExit = { LandingActivity.clearLaunch(context) }
                )
            }

            AnimatedVisibility(visible = screenState.isPaused) {
                PauseBox(
                    isQuitting = screenState.isQuitting,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                    onResume = {
                        gameViewModel.onEvent(
                            GameScreenEvent.PauseStateChanged(
                                isPaused = false,
                                isLeaving = false
                            )
                        )
                    },
                    onExit = { LandingActivity.clearLaunch(context) }
                )
            }
        }
    }

    BackHandler {
        gameViewModel.onEvent(
            GameScreenEvent.PauseStateChanged(
                isPaused = !screenState.isPaused,
                isLeaving = !screenState.isPaused
            )
        )
    }
}

@Composable
private fun AppBar(
    areIconsVisible: Boolean,
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
        if (areIconsVisible) {
            IconButton(
                onClick = onBackPress,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }

            IconButton(
                onClick = onPausePress,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = "Pause",
                    tint = Color.White,
                )
            }
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

@Composable
private fun TilesGrid(
    screenState: GameScreenState,
    gameMode: GameMode,
    timerValue: String,
    onTileFlip: (GameScreenEvent.TileFlipped) -> Unit,
) {
    val tilesList = screenState.tilesStateList
    val nColumns = if (isTablet()) {
        when (gameMode) {
            GameMode.EASY -> 4
            GameMode.MEDIUM -> 5
            GameMode.HARD -> 6
        }
    } else {
        when (gameMode) {
            GameMode.EASY -> 4
            GameMode.MEDIUM -> 4
            GameMode.HARD -> 6
        }
    }

    Text(
        text = timerValue,
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
        text = "Flips: ${screenState.nFlips}",
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        items(tilesList.size) { index ->
            val tileState = tilesList[index]

            ImageTile(
                onClick = {
                    onTileFlip(GameScreenEvent.TileFlipped(tileState, index))
                },
                tileState = tileState
            )
        }
    }
}

@Composable
private fun ConfettiBox(
    confettiState: ConfettiState,
    modifier: Modifier = Modifier,
    onConfettiEnded: () -> Unit
) {
    Box(modifier = modifier) {
        if (confettiState is ConfettiState.Started) {
            KonfettiView(
                parties = confettiState.party,
                modifier = Modifier.fillMaxSize(),
                updateListener = object : OnParticleSystemUpdateListener {
                    override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                        if (activeSystems == 0) {
                            onConfettiEnded()
                        }
                    }
                }
            )
        }
    }
}