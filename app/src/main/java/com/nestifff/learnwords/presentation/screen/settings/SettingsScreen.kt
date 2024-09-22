package com.nestifff.learnwords.presentation.screen.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.NavigateBack
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.UpdateSuccessMessage
import com.nestifff.learnwords.presentation.ui.components.common.PrimarySnackbarHost
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTopBar
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTwoButtonsDialog
import com.nestifff.learnwords.presentation.ui.components.screens.settings.GeneralSettingsDataComponent
import com.nestifff.learnwords.presentation.ui.components.screens.settings.LearnProcessSettingsDataComponent
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    onEffect(effect = viewModel.uiEffect) {
        when (it) {
            NavigateBack -> navigateBack()
            UpdateSuccessMessage ->
                snackbarHostState.showSnackbar("New settings were successfully saved")
        }
    }

    SettingsScreenContent(state, snackbarHostState, viewModel)

    BackHandler {
        viewModel.onBackTriggered()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenContent(
    state: SettingsViewModel.State,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingsViewModel
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background)
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 16.dp),
        topBar = {
            PrimaryTopBar(
                title = "Settings",
                onNavigationButtonClick = { viewModel.onBackTriggered() }
            )
        },
        snackbarHost = { PrimarySnackbarHost(hostState = snackbarHostState) },
        containerColor = AppTheme.colors.background
    ) { scaffoldPaddings ->
        Column(
            modifier = Modifier
                .padding(scaffoldPaddings)
                .verticalScroll(rememberScrollState())
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
}
