package com.nestifff.learnwords.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.NavigateBack
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTextField
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.model.SettingsDomain

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) {
        when (it) {
            NavigateBack -> navigateBack()
        }
    }

    SettingsScreenContent(state, viewModel)
}

@Composable
private fun SettingsScreenContent(
    state: SettingsViewModel.State,
    viewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background)
            .systemBarsPadding()
            .padding(horizontal = 32.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Settings",
                style = AppTheme.typography.h0MediumTextStyle,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            SettingsDataComponent(
                state = state
            )
        }

        PrimaryButton(
            text = "Save",
            onClick = { viewModel.onSaveClicked() },
            isEnabled = state.isSaveButtonEnabled,
            isLoading = state.isSavingInProgress,
            modifier = Modifier
                .widthIn(min = 112.dp)
                .align(Alignment.End)
        )
    }
}

@Composable
private fun SettingsDataComponent(
    state: SettingsViewModel.State
) {
    if (state.currentSettings == null) {
        return
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, AppTheme.colors.backgroundMedium, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Learn process",
            style = AppTheme.typography.h2BoldTextStyle.copy(
                AppTheme.colors.contentLight
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Default number of words to learn",
                style = AppTheme.typography.h2MediumTextStyle,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f)
            )
            PrimaryTextField(
                value = (state.updatedNumberToLearn
                    ?: state.currentSettings.defaultNumberToLearn).toString(),
                onValueChange = {},
                modifier = Modifier
                    .width(64.dp)
                    .heightIn(min = 48.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Count to enter on 1st try",
                style = AppTheme.typography.h2MediumTextStyle,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f)
            )
            PrimaryTextField(
                value = (state.updatedNumberToLearn
                    ?: state.currentSettings.defaultNumberToLearn).toString(),
                onValueChange = {},
                modifier = Modifier
                    .width(64.dp)
                    .heightIn(min = 48.dp)
            )
        }
        Text(
            text = "After you enter word correctly on 1st try this amount of times, it will be moved to Learned",
            style = AppTheme.typography.h3RegularTextStyle.copy(
                color = AppTheme.colors.contentLight
            ),
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsData_Preview() {
    ThemeProvider {
        SettingsDataComponent(
            state = SettingsViewModel.State(
                currentSettings = SettingsDomain(
                    defaultNumberToLearn = 15,
                    defaultWayToLearn = WayToLearnDomain.ENG_TO_RUS,
                    countOnFirstTryToMoveToLearned = 3,
                    isDarkMode = false
                )
            )
        )
    }
}
