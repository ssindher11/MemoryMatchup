package com.cutetech.memorymatchup.presentation.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.presentation.SpringButton
import com.cutetech.memorymatchup.presentation.level.ChooseLevelActivity

@Composable
fun LandingScreen() {
    val context = LocalContext.current

    BackgroundGradient {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topImageRef, titleRef, playBtnRef, coopBtnRef, bottomImageRef) = createRefs()

            Image(
                imageVector = ImageVector.vectorResource(id = drawable.corner_illustration),
                contentDescription = "",
                modifier = Modifier.constrainAs(topImageRef) {
                    top.linkTo(parent.top, 2.dp)
                    start.linkTo(parent.start, 2.dp)
                }
            )

            TitleAndLogo(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(topImageRef.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
            )

            SpringButton(
                text = stringResource(id = string.play),
                modifier = Modifier.constrainAs(playBtnRef) {
                    top.linkTo(titleRef.bottom, 64.dp)
                    start.linkTo(parent.start, 64.dp)
                    end.linkTo(parent.end, 64.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                ChooseLevelActivity.launch(context)
            }

            /*SpringButton(
                text = "PLAY CO-OP",
                modifier = Modifier.constrainAs(coopBtnRef) {
                    top.linkTo(playBtnRef.bottom, 24.dp)
                    start.linkTo(parent.start, 64.dp)
                    end.linkTo(parent.end, 64.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                // TODO
            }*/

            Image(
                imageVector = ImageVector.vectorResource(id = drawable.corner_illustration),
                contentDescription = "",
                modifier = Modifier
                    .rotate(180f)
                    .constrainAs(bottomImageRef) {
                        bottom.linkTo(parent.bottom, 2.dp)
                        end.linkTo(parent.end, 2.dp)
                    }
            )
        }
    }
}

@Composable
private fun TitleAndLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = string.memory_matchup),
            style = MaterialTheme.typography.displaySmall.copy(
                letterSpacing = 1.1.sp,
                lineHeight = 44.sp
            ),
            color = Color.White,
        )

        Image(
            imageVector = ImageVector.vectorResource(id = drawable.home_illustration),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen()
}