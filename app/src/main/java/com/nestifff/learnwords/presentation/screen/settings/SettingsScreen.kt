package com.nestifff.learnwords.presentation.screen.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.ErrorZeroOrEmptyValuesMessage
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.NavigateBack
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.UpdateSuccessMessage
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTopBar
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTwoButtonsDialog
import com.nestifff.learnwords.presentation.ui.components.screens.settings.GeneralSettingsDataComponent
import com.nestifff.learnwords.presentation.ui.components.screens.settings.LearnProcessSettingsDataComponent
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.utils.showToast

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    onEffect(effect = viewModel.uiEffect) {
        when (it) {
            NavigateBack -> navigateBack()
            ErrorZeroOrEmptyValuesMessage -> context.showToast("Values can't be blank or 0")
            UpdateSuccessMessage -> context.showToast("New settings were successfully saved")
        }
    }

    SettingsScreenContent(state, viewModel)

    BackHandler {
        viewModel.onBackTriggered()
    }
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
            .imePadding()
            .padding(horizontal = 16.dp),
    ) {
        PrimaryTopBar(
            title = "Settings",
            onNavigationButtonClick = { viewModel.onBackTriggered() }
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            LearnProcessSettingsDataComponent(
                state = state,
                onNumberToLearnChange = { viewModel.onNumberToLearnChanged(it) },
                onCountOnFirstTryChange = { viewModel.onCountOnFirstTryChanged(it) },
                onWayToLearnSelect = { viewModel.onWayToLearnSelected(it) },
                onSelectWayToLearnClick = { viewModel.onSelectWayToLearnClicked() },
                onWayToLearnMenuDismiss = { viewModel.onWayToLearnMenuDismiss() },
                onUpdateClick = { viewModel.onUpdateLearnProcessClicked() },
            )
            GeneralSettingsDataComponent(
                isDarkMode = state.currentSettings?.isDarkMode,
                onDarkModeChange = { viewModel.onDarkModeChanged(it) },
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }

    if (state.isConfirmExitDialogVisible) {
        PrimaryTwoButtonsDialog(
            text = "Are you sure you want to exit?",
            positiveButtonText = "Exit",
            negativeButtonText = "Cancel",
            onPositiveClick = { viewModel.onConfirmExitDialogExitClicked() },
            onNegativeClick = { viewModel.onDismissConfirmExitDialog() },
            onDismiss = { viewModel.onDismissConfirmExitDialog() },
            descriptionText = "All your changes will not be saved"
        )
    }
}
