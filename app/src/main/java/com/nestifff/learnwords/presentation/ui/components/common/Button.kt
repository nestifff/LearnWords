package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isEnabled) {
                    Modifier
                        .background(color = AppTheme.colors.primaryLight)
                        .clickable { onClick() }
                } else {
                    Modifier
                        .background(color = AppTheme.colors.backgroundMedium)
                        .noRippleClickable {}
                }
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = AppTheme.typography.h2MediumTextStyle,
            color = AppTheme.colors.content,
        )
    }
}

@Preview
@Composable
fun PrimaryButton_Preview(modifier: Modifier = Modifier) {
    ThemeProvider {
        PrimaryButton(
            text = "Enabled button",
            onClick = {}
        )
    }
}