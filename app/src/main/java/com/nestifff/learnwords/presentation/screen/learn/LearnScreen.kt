package com.nestifff.learnwords.presentation.screen.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.emptyString
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel.State
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.components.screens.learn.LearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.learn.ResultAnimation
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background)
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 64.dp)
        ) {
            Text(
                text = state.word?.shownValue ?: "",
                style = AppTheme.typography.h1MediumTextStyle,
                color = AppTheme.colors.content,
            )
            WordsTextField(
                value = state.word?.enteredValue ?: "",
                onValueChange = { viewModel.onEnteredValueChanged(it) },
                modifier = Modifier.padding(top = 32.dp),
                isEnabled = state.isEnteringWordEnabled
            )

            ResultAnimation(
                state = state.resulAnimationState,
                onAnimationFinish = {},
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
            )
        }

        LearnButton(
            state = state.buttonState,
            onClick = { viewModel.onButtonClicked() },
            modifier = Modifier.padding(bottom = 32.dp),
        )
    }
}
