package com.cutetech.memorymatchup.utils

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
}

@Composable
inline fun <T> ResourceStateRender(
    state: State<Resource<T>>,
    onError: @Composable ((String?) -> Unit) = { OnError(it ?: "Something went wrong") },
    onLoading: @Composable (() -> Unit) = { OnLoading() },
    onSuccess: @Composable (T) -> Unit,
) {
    when (val itemValue = state.value) {
        is Resource.Success -> {
            itemValue.data?.let {
                onSuccess.invoke(it)
            }
        }
        is Resource.Error -> onError.invoke(
            itemValue.message
        )
        is Resource.Loading -> onLoading.invoke()
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(44.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun OnError(error: String, pressBack: Boolean = true) {
    if (pressBack) {
        val backDispatcher = LocalOnBackPressedDispatcherOwner.current
        backDispatcher?.onBackPressedDispatcher?.onBackPressed()
    }
    val context = LocalContext.current
    showToast(context, error)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}