package com.cutetech.memorymatchup.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LifecycleLaunchedEffect(
    vararg keys: Any,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME,
    onLifecycleEvent: suspend CoroutineScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val currentLifecycleEventCallback = rememberUpdatedState(newValue = onLifecycleEvent)
    val lifecycleEventObserver = remember {
        LifecycleEventObserver { _, event ->
            if (event == lifecycleEvent) {
                coroutineScope.launch(block = currentLifecycleEventCallback.value)
            }
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val newKeys = mutableListOf(elements = keys)
    newKeys.add(lifecycle)
    newKeys.add(lifecycleEventObserver)
    DisposableEffect(keys = newKeys.toTypedArray()) {
        lifecycle.addObserver(lifecycleEventObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}