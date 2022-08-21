package com.cutetech.memorymatchup.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.ui.theme.AccentBlue
import com.cutetech.memorymatchup.ui.theme.ErrorRed
import com.cutetech.memorymatchup.ui.theme.vanillaDreamersFontFamily

@Composable
fun GameOverBox(
    gameScore: Int,
    modifier: Modifier = Modifier,
    onNewGame: () -> Unit,
    onExit: () -> Unit,
) {
    val scoreText = when (gameScore) {
        1 -> stringResource(id = string.well_done)
        2 -> stringResource(id = string.great_game)
        3 -> stringResource(id = string.excellent)
        else -> stringResource(id = string.great_game)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AccentBlue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {}
    ) {
        BlurredBox()

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = scoreText,
                color = Color.Black,
                fontSize = 64.sp,
                fontFamily = vanillaDreamersFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 64.dp,
                        end = 16.dp,
                    )
            )

            StarsRow(
                gameScore = gameScore,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(CenterHorizontally)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp)
                    .padding(top = 40.dp, bottom = 24.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Button(
                    onClick = onNewGame,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                ) {
                    Text(
                        text = stringResource(id = string.new_game),
                        fontSize = 28.sp,
                        fontFamily = vanillaDreamersFontFamily,
                    )
                }

                Button(
                    onClick = onExit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                ) {
                    Text(
                        text = stringResource(id = string.exit),
                        fontSize = 28.sp,
                        fontFamily = vanillaDreamersFontFamily,
                    )
                }
            }
        }
    }
}

@Composable
private fun StarsRow(
    gameScore: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Star(
            isFilled = gameScore >= 1,
            modifier = Modifier.padding(top = 20.dp)
        )

        Star(
            isFilled = gameScore >= 3,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Star(
            isFilled = gameScore >= 2,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
private fun Star(
    isFilled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = drawable.star_empty),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )

        AnimatedVisibility(
            visible = isFilled,
            modifier = Modifier.fillMaxSize(),
            enter = expandIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                )
            )
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = drawable.star_filled),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StarsRowPreview() {
    StarsRow(gameScore = 2)
}