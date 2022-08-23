package com.cutetech.memorymatchup.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.ui.theme.AccentBlue
import com.cutetech.memorymatchup.ui.theme.ErrorRed
import com.cutetech.memorymatchup.ui.theme.vanillaDreamersFontFamily
import com.cutetech.memorymatchup.utils.LinearGradient

@Composable
fun PauseBox(
    modifier: Modifier = Modifier,
    isQuitting: Boolean = false,
    onResume: () -> Unit,
    onExit: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {}
    ) {
        val (textRef, buttonsRef, blurredBoxRef) = createRefs()

        BlurredBox(
            modifier = Modifier
                .background(AccentBlue)
                .constrainAs(blurredBoxRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.start)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = stringResource(id = if (isQuitting) string.leave_game else string.paused),
            color = Color.Black,
            fontSize = 57.sp,
            fontFamily = vanillaDreamersFontFamily,
            lineHeight = 64.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textRef) {
                    top.linkTo(parent.top, 56.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(buttonsRef) {
                    start.linkTo(parent.start, 56.dp)
                    end.linkTo(parent.end, 56.dp)
                    linkTo(
                        top = textRef.bottom,
                        bottom = parent.bottom,
                        topMargin = 80.dp,
                        bottomMargin = 48.dp,
                    )
                    width = Dimension.fillToConstraints
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(
                onClick = onResume,
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
                    text = stringResource(id = if (isQuitting) string.no else string.resume),
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
                    text = stringResource(id = if (isQuitting) string.yes else string.exit),
                    fontSize = 28.sp,
                    fontFamily = vanillaDreamersFontFamily,
                )
            }
        }
    }
}


@Composable
fun BlurredBox(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    brush = LinearGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0x00FFFFFF), Color(0xFFFFFFFF)),
                        angleInDegrees = 135f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    brush = LinearGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0x00FFFFFF), Color(0xFFFFFFFF)),
                        angleInDegrees = 45f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    brush = Brush.radialGradient(listOf(Color(0xFFFFFFFF), Color(0x00FFFFFF)))
                )
        )
    }
}


@Preview
@Composable
private fun PauseBoxPreview() {
    BackgroundGradient {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PauseBox(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onResume = {},
                onExit = {}
            )
        }
    }
}
