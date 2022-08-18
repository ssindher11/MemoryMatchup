package com.cutetech.memorymatchup.presentation.game

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.ui.theme.AccentBlue
import com.cutetech.memorymatchup.ui.theme.museoFontFamily
import com.cutetech.memorymatchup.utils.LinearGradient

@Composable
fun PauseBox(
    modifier: Modifier = Modifier,
    onResume: () -> Unit,
    onExit: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Color.Transparent else AccentBlue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {}
    ) {
        BlurredBox()

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = string.paused),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = museoFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 64.dp,
                        end = 16.dp,
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp, vertical = 100.dp)
                    .align(Alignment.BottomCenter),
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
                ) {
                    Text(
                        text = stringResource(id = string.resume),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = museoFontFamily,
                    )
                }

                Button(
                    onClick = onExit,
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
                ) {
                    Text(
                        text = stringResource(id = string.exit),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = museoFontFamily,
                    )
                }
            }
        }
    }
}


@Composable
private fun BlurredBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            .background(
                brush = LinearGradient(
                    colors = listOf(Color(0x66FFFFFF), Color(0x00FFFFFF), Color(0x66FFFFFF)),
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
                    colors = listOf(Color(0x66FFFFFF), Color(0x00FFFFFF), Color(0x66FFFFFF)),
                    angleInDegrees = 45f
                )
            )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            .background(
                brush = Brush.radialGradient(listOf(Color(0x66FFFFFF), Color(0x00FFFFFF)))
            )
    )
}


@Preview
@Composable
private fun PauseBoxPreview() {
    BackgroundGradient {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(AccentBlue),
            contentAlignment = Alignment.Center
        ) {

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            )
            PauseBox(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth(),
                onResume = {},
                onExit = {}
            )
        }
    }
}
