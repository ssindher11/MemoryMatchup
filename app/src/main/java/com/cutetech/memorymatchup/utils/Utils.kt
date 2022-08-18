package com.cutetech.memorymatchup.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import com.cutetech.memorymatchup.R.bool

@ReadOnlyComposable
@Composable
fun isTablet(): Boolean = LocalContext.current.isLargeLandscapeDevice()

fun Context.isLargeLandscapeDevice(): Boolean {
    return resources?.getBoolean(bool.is_large_landscape_device) ?: false
}