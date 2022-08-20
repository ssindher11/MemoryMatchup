package com.cutetech.memorymatchup.presentation.level

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cutetech.memorymatchup.R.drawable
import com.cutetech.memorymatchup.R.string
import com.cutetech.memorymatchup.presentation.BackgroundGradient
import com.cutetech.memorymatchup.presentation.SpringButton

@Composable
fun ChooseLevelScreen() {
    BackgroundGradient {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topImageRef, levelColumnRef, bottomImageRef) = createRefs()

            Image(
                imageVector = ImageVector.vectorResource(id = drawable.corner_illustration),
                contentDescription = "",
                modifier = Modifier.constrainAs(topImageRef) {
                    top.linkTo(parent.top, 2.dp)
                    start.linkTo(parent.start, 2.dp)
                }
            )

            LevelsColumn(
                modifier = Modifier
                .fillMaxWidth()
                .constrainAs(levelColumnRef) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.4f
                    )
                }
            )

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
fun LevelsColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = string.choose_level),
            style = MaterialTheme.typography.displaySmall.copy(
                letterSpacing = 1.1.sp,
                lineHeight = 44.sp
            ),
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SpringButton(
            text = stringResource(id = string.easy),
            modifier = Modifier
                .padding(horizontal = 64.dp)
                .padding(top = 48.dp)
        ) {
            // TODO
        }

        SpringButton(
            text = stringResource(id = string.medium),
            modifier = Modifier
                .padding(horizontal = 64.dp)
                .padding(top = 28.dp)
        ) {
            // TODO
        }

        SpringButton(
            text = stringResource(id = string.hard),
            modifier = Modifier
                .padding(horizontal = 64.dp)
                .padding(top = 28.dp)
        ) {
            // TODO
        }
    }
}

@Preview
@Composable
private fun ChooseLevelScreenPreview() {
    ChooseLevelScreen()
}