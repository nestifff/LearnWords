package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun SaveChangedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (enabled) {
                    Modifier
                        .background(color = AppTheme.colors.primaryLight)
                        .clickable { onClick() }
                } else {
                    Modifier
                        .background(color = AppTheme.colors.backgroundMedium)
                        .noRippleClickable {}
                }
            )
            .padding(vertical = 6.dp, horizontal = 12.dp)

    ) {
        Text(
            text = "Save",
            style = AppTheme.typography.h2MediumTextStyle,
            color = AppTheme.colors.text,
        )
    }
}
