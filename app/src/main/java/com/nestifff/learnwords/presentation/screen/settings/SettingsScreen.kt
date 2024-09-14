package com.nestifff.learnwords.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.NavigateBack
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
        SettingsDataComponent(
            state = state
        )

    }
}

@Composable
private fun SettingsDataComponent(
    state: SettingsViewModel.State
) {

}

@Preview
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
