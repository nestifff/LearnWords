package com.nestifff.learnwords.presentation.ui.components.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import com.nestifff.learnwords.presentation.ui.components.common.EditableWayToLearn
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryNumbersTextField
import com.nestifff.learnwords.presentation.ui.components.common.PrimarySwitch
import com.nestifff.learnwords.presentation.ui.components.common.DataSectionTitle
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.dataSectionShape
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.model.SettingsDomain

@Composable
fun GeneralSettingsDataComponent(
    isDarkMode: Boolean?,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isDarkMode == null) {
        return
    }
    Column(
        modifier = modifier.dataSectionShape()
    ) {
        DataSectionTitle(
            text = "General"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SettingItemTitle(text = "Dark mode")
            PrimarySwitch(
                isChecked = isDarkMode,
                onCheckedChange = onDarkModeChange
            )
        }
    }
}

@Composable
fun LearnProcessSettingsDataComponent(
    state: SettingsViewModel.State,
    onNumberToLearnChange: (String) -> Unit,
    onCountOnFirstTryChange: (String) -> Unit,
    onWayToLearnSelect: (WayToLearnDomain) -> Unit,
    onSelectWayToLearnClick: () -> Unit,
    onWayToLearnMenuDismiss: () -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.currentSettings == null) {
        return
    }
    Column(
        modifier = modifier.dataSectionShape()
    ) {
        DataSectionTitle(
            text = "Learn process"
        )
        val numberToLearn =
            state.updatedNumberToLearn ?: state.currentSettings.defaultNumberToLearn.toString()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SettingItemTitle(
                text = "Default number of words to learn"
            )
            PrimaryNumbersTextField(
                value = numberToLearn,
                onValueChange = onNumberToLearnChange
            )
        }
        val wayToLearn = state.updatedWayToLearn ?: state.currentSettings.defaultWayToLearn
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SettingItemTitle(
                text = "Default way to learn"
            )
            EditableWayToLearn(
                currentWayToLearn = wayToLearn,
                isExpandedMenuVisible = state.isChangeWayToLearnMenuVisible,
                onWayToLearnSelect = onWayToLearnSelect,
                onMenuDismiss = onWayToLearnMenuDismiss,
                onOpenMenuClick = onSelectWayToLearnClick
            )
        }
        DescriptionText(
            text = when (wayToLearn) {
                WayToLearnDomain.WRITE_LEARNING_VALUE -> "While learning you see native language translation and you need to enter word"
                WayToLearnDomain.WRITE_TRANSLATION -> "While learning you see word to learn and you need to enter native language translation"
            },
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )
        val countOnFirstTry = state.updatedCountOnFirstTry
            ?: state.currentSettings.countOnFirstTryToMoveToLearned.toString()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SettingItemTitle(
                text = "Count to enter on 1st try"
            )
            PrimaryNumbersTextField(
                value = countOnFirstTry,
                onValueChange = onCountOnFirstTryChange
            )
        }
        DescriptionText(
            text = "After you enter word correctly on 1st try this amount of times, it will be moved to Learned",
        )

        PrimaryButton(
            text = "Update",
            onClick = onUpdateClick,
            modifier = Modifier
                .padding(top = 20.dp)
                .widthIn(min = 102.dp)
                .align(Alignment.End),
            isEnabled = state.isUpdateLearnProcessEnabled
        )
    }
}


@Composable
private fun RowScope.SettingItemTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTheme.typography.h2MediumTextStyle,
        color = AppTheme.colors.content,
        modifier = modifier
            .weight(1f)
            .padding(end = 16.dp)
    )
}

@Composable
private fun DescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    isWarning: Boolean = false
) {
    Text(
        text = text,
        style = AppTheme.typography.h3RegularTextStyle.copy(
            color = if (isWarning) AppTheme.colors.warning else AppTheme.colors.contentLight
        ),
        modifier = modifier.fillMaxWidth(0.7f)
    )
}

@Preview(showBackground = true)
@Composable
private fun GeneralSettingsData_Preview() {
    ThemeProvider {
        GeneralSettingsDataComponent(
            isDarkMode = true, onDarkModeChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LearnProcessSettingsData_Preview() {
    ThemeProvider {
        LearnProcessSettingsDataComponent(
            state = SettingsViewModel.State(
                currentSettings = SettingsDomain(
                    defaultNumberToLearn = 15,
                    defaultWayToLearn = WayToLearnDomain.WRITE_LEARNING_VALUE,
                    countOnFirstTryToMoveToLearned = 3,
                    isDarkMode = false
                )
            ),
            onNumberToLearnChange = {},
            onCountOnFirstTryChange = {},
            onWayToLearnSelect = {},
            onSelectWayToLearnClick = {},
            onWayToLearnMenuDismiss = {},
            onUpdateClick = {}
        )
    }
}

