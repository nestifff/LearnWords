package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimarySwitch(
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier
) {
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colors.switcherThumb, // todo lalala mb separate and lignt in both modes
            uncheckedThumbColor = AppTheme.colors.contentLight,
            uncheckedBorderColor = AppTheme.colors.contentLight,
            checkedTrackColor = AppTheme.colors.primary,
            uncheckedTrackColor = AppTheme.colors.textFieldBackground,
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PrimarySwitch_On_Preview() {
    ThemeProvider {
        PrimarySwitch(true, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimarySwitch_Off_Preview() {
    ThemeProvider {
        PrimarySwitch(false, {})
    }
}