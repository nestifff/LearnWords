package com.nestifff.learnwords.presentation.screen.learn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nestifff.learnwords.ext.onEffect

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

}
