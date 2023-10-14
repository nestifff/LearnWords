package com.nestifff.learnwords.presentation.screen.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel.State
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.components.screens.learn.LearnButton
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
        onEnteredValueChange = { viewModel.onEnteredValueChanged(it) },
        onNextButtonClick = { viewModel.onNextButtonClicked() }
    )
}

@Composable
fun LearnScreenContent(
    state: State,
    onEnteredValueChange: (String) -> Unit,
    onNextButtonClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

//        AnimatedContent(targetState = state, label = "") {
            when (state) {
                is State.Display -> {

                    Column {

                        Text(
                            text = state.word.shownValue,
                            style = AppTheme.typography.h1MediumTextStyle,
                            color = AppTheme.colors.text,
                            modifier = Modifier.padding(top = 64.dp)
                        )

                        WordsTextField(
                            value = state.word.enteredValue,
                            onValueChange = onEnteredValueChange,
                            modifier = Modifier.padding(top = 32.dp)
                        )

                        Text(
                            text = state.isCorrect?.toString() ?: "null",
                            modifier = Modifier.padding(top = 32.dp)
                        )

                    }
                }

                is State.Loading -> {
                    CircularProgressIndicator(color = AppTheme.colors.secondary)
                }
            }
//        }

        LearnButton(
            state = LearnButtonState(true, LearnNextButtonType.Next),
            onClick = onNextButtonClick,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}
