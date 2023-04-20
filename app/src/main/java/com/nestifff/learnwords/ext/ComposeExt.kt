package com.nestifff.learnwords.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.nestifff.learnwords.app.App
import com.nestifff.learnwords.app.core.UiEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun <T: UiEffect> onEffect(effect: SharedFlow<T>, block: (T) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        effect.collect {
            block(it)
        }
    }
}

@Composable
fun getApplication(): App {
    return LocalContext.current.applicationContext as App
}