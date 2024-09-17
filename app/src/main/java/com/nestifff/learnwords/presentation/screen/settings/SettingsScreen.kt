package com.nestifff.learnwords.presentation.screen.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.ErrorZeroOrEmptyValuesMessage
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.NavigateBack
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel.Effect.SaveSuccessMessage
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTwoButtonsDialog
import com.nestifff.learnwords.presentation.ui.components.screens.settings.SettingsDataComponent
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
            SaveSuccessMessage -> context.showToast("New settings were successfully saved")
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
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // top bar
            Row(
                modifier = Modifier.padding(bottom = 32.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.onBackTriggered() }
                        .padding(4.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = AppTheme.colors.content
                )
                Text(
                    text = "Settings",
                    style = AppTheme.typography.h0MediumTextStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            SettingsDataComponent(
                state = state,
                modifier = Modifier.padding(bottom = 32.dp),
                onNumberToLearnChange = { viewModel.onNumberToLearnChanged(it) },
                onCountOnFirstTryChange = { viewModel.onCountOnFirstTryChanged(it) },
                onWayToLearnSelect = { viewModel.onWayToLearnSelected(it) },
                onSelectWayToLearnClick = { viewModel.onSelectWayToLearnClicked() },
                onWayToLearnMenuDismiss = { viewModel.onWayToLearnMenuDismiss() },
                onDarkModeChange = { viewModel.onDarkModeChanged(it) },
            )
        }

        PrimaryButton(
            text = "Save",
            onClick = { viewModel.onSaveClicked() },
            isEnabled = state.isSaveButtonEnabled,
            isLoading = state.isSavingInProgress,
            modifier = Modifier
                .padding(bottom = 32.dp, end = 16.dp)
                .widthIn(min = 112.dp)
                .align(Alignment.End)
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
