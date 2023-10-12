package com.nestifff.learnwords.presentation.screen.learn

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun LearnScreen(
    viewModel: LearnViewModel
) {

    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) { effect ->
        when (effect) {
            else -> {}
        }
    }

    LearnScreenContent(
        state = state,
    )

}

@Composable
fun LearnScreenContent(
    state: LearnViewModel.State,
) {
    Log.i("Lalala", "LearnScreenContent: state = $state")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.secondary)
    ) {

    }
}
