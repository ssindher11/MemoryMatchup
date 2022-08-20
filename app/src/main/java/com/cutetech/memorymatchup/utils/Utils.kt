package com.cutetech.memorymatchup.utils

import android.content.Context
import android.content.Intent
import android.os.Build
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

const val PARAMS = "params"

inline fun <reified T> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as T?
}