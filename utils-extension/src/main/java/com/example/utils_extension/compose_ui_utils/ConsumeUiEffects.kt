package com.example.utils_extension.compose_ui_utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> ConsumeUIEffects(
    uiEvents: Flow<T>,
    shouldBeLifecycleAware:Boolean=false,
    consumer: (T,CoroutineScope) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(uiEvents) {
        if (shouldBeLifecycleAware){
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                uiEvents.collectLatest {
                    consumer(it,this)
                }
            }
        }else{
            uiEvents.collectLatest { consumer(it,this) }
        }
    }
}