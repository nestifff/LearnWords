package com.nestifff.learnwords.presentation.screen.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel.Effect.NavigateToResultScreen
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.getTextFieldColors
import com.nestifff.learnwords.presentation.ui.components.screens.learn.LearnProgressTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.learn.AnswerResultComponent
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun LearnScreen(
    viewModel: LearnViewModel,
    navigateToResultScreen: () -> Unit,
) {

    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) { effect ->
        when (effect) {
            NavigateToResultScreen -> navigateToResultScreen()
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
        Column {
            LearnProgressTopBar(
                state = state.progressState,
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            )
            Text(
                text = state.word?.shownValue ?: "",
                style = AppTheme.typography.h1MediumTextStyle,
                color = AppTheme.colors.content,
            )

            val focusRequester = remember { FocusRequester() }
            val keyboard = LocalSoftwareKeyboardController.current
            LearnTextField(
                value = state.word?.enteredValue ?: "",
                onValueChange = { viewModel.onEnteredValueChanged(it) },
                isEnabled = state.isEnteringWordEnabled,
                focusRequester = focusRequester
            )

            AnswerResultComponent(state = state.resulAnimationState)

            LaunchedEffect(key1 = state.word) {
                if (state.word != null) {
                    focusRequester.requestFocus()
                    delay(100)
                    keyboard?.show()
                }
            }
        }

        PrimaryButton(
            text = state.buttonState.getText(),
            onClick = { viewModel.onButtonClicked() },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .height(54.dp),
            isEnabled = state.buttonState.isEnabled,
            isLoading = state.buttonState.isLoading
        )
    }
}

// we don't put enabled in TextField but instead just change colors and don't invoke onValueChange
// this is to prevent keyboard from closing when it's disabled
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LearnTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean,
    focusRequester: FocusRequester,
) {
    TextField(
        value = value,
        onValueChange = {
            if (isEnabled) {
                onValueChange(it)
            }
        },
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = if (isEnabled) {
            AppTheme.typography.h2RegularTextStyle
        } else {
            AppTheme.typography.h2RegularTextStyle.copy(
                color = AppTheme.colors.content.copy(alpha = 0.5f)
            )
        },
        colors = if (isEnabled) {
            getTextFieldColors()
        } else {
            getTextFieldColors(
                cursorColor = Color.Transparent,
                selectionColors = TextSelectionColors(
                    handleColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                )
            )
        }
    )
}

private fun LearnButtonState.getText(): String {
    return when (this.type) {
        LearnNextButtonType.GoToNextWord -> "Next"
        LearnNextButtonType.CheckAnswer -> "Check"
    }
}

