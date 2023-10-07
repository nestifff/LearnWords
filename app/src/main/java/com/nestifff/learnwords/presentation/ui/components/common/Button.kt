package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isEnabled) {
                    Modifier
                        .background(color = WordsTheme.colors.primaryLight)
                        .clickable { onClick() }
                } else {
                    Modifier
                        .background(color = WordsTheme.colors.backgroundMedium)
                        .noRippleClickable {}
                }
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}