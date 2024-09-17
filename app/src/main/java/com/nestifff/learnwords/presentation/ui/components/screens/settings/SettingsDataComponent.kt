package com.nestifff.learnwords.presentation.ui.components.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import com.nestifff.learnwords.presentation.ui.components.common.EditableWayToLearn
import com.nestifff.learnwords.presentation.ui.components.common.PrimarySwitch
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTextField
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.model.SettingsDomain

@Composable
fun SettingsDataComponent(
    state: SettingsViewModel.State,
    onNumberToLearnChange: (String) -> Unit,
    onCountOnFirstTryChange: (String) -> Unit,
    onWayToLearnSelect: (WayToLearnDomain) -> Unit,
    onSelectWayToLearnClick: () -> Unit,
    onWayToLearnMenuDismiss: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.currentSettings == null) {
        return
    }
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, AppTheme.colors.backgroundMedium, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            SectionTitle(
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
                SettingItemNumberTextField(
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
                    WayToLearnDomain.RUS_TO_ENG -> "While learning you see native language translation and you need to enter word"
                    WayToLearnDomain.ENG_TO_RUS -> "While learning you see word to learn and you need to enter native language translation"
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
                SettingItemNumberTextField(
                    value = countOnFirstTry,
                    onValueChange = onCountOnFirstTryChange
                )
            }
            DescriptionText(
                text = "After you enter word correctly on 1st try this amount of times, it will be moved to Learned",
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, AppTheme.colors.backgroundMedium, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            SectionTitle(
                text = "General"
            )
            val isDarkMode = state.updatedDarkMode ?: state.currentSettings.isDarkMode
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
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTheme.typography.h2BoldTextStyle.copy(
            AppTheme.colors.contentLight
        ),
        modifier = modifier.padding(bottom = 24.dp)
    )
}

@Composable
private fun RowScope.SettingItemTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTheme.typography.h2MediumTextStyle,
        modifier = modifier
            .weight(1f)
            .padding(end = 16.dp)
    )
}

@Composable
fun SettingItemNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pattern = remember { Regex("^\\d+\$") }
    PrimaryTextField(
        value = value,
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .width(64.dp)
            .heightIn(min = 48.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )
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
private fun SettingsData_Preview() {
    ThemeProvider {
        SettingsDataComponent(
            state = SettingsViewModel.State(
                currentSettings = SettingsDomain(
                    defaultNumberToLearn = 15,
                    defaultWayToLearn = WayToLearnDomain.RUS_TO_ENG,
                    countOnFirstTryToMoveToLearned = 3,
                    isDarkMode = false
                )
            ),
            onNumberToLearnChange = {},
            onCountOnFirstTryChange = {},
            onWayToLearnSelect = {},
            onSelectWayToLearnClick = {},
            onWayToLearnMenuDismiss = {},
            onDarkModeChange = {},
        )
    }
}
