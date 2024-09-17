package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    enabledBackgroundColor: Color = AppTheme.colors.primaryLight
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isEnabled) {
                    Modifier
                        .background(color = enabledBackgroundColor)
                        .clickable { onClick() }
                } else {
                    Modifier
                        .background(color = AppTheme.colors.backgroundMedium)
                        .noRippleClickable {}
                }
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = AppTheme.typography.h2MediumTextStyle,
                color = AppTheme.colors.content,
            )
            if (isLoading) {
                CircularProgressIndicator(color = AppTheme.colors.primary)
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryButton_Enabled_Preview() {
    ThemeProvider {
        PrimaryButton(
            text = "I'm a button",
            onClick = {},
            modifier = Modifier.width(260.dp)
        )
    }
}

@Preview
@Composable
private fun PrimaryButton_Disabled_Preview() {
    ThemeProvider {
        PrimaryButton(
            text = "I'm a button",
            onClick = {},
            isEnabled = false,
            modifier = Modifier.width(260.dp)
        )
    }
}

@Preview
@Composable
private fun PrimaryButton_Disabled_Loading_Preview() {
    ThemeProvider {
        PrimaryButton(
            text = "I'm a button",
            onClick = {},
            isEnabled = false,
            isLoading = true,
            modifier = Modifier.width(260.dp)
        )
    }
}
