package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun CollectionLearnButton(
    onClick: () -> Unit,
    onCustomizeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bigButtonSize = 64.dp
    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .height(bigButtonSize + 14.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(bigButtonSize)
                .clip(CircleShape)
                .background(color = AppTheme.colors.primary)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = AppTheme.colors.content,
                modifier = Modifier.size(32.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(28.dp)
                .clip(CircleShape)
                .background(color = AppTheme.colors.primaryLight)
                .clickable { onCustomizeClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = AppTheme.colors.content,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CollectionLearnButton_Preview() {
    ThemeProvider {
        CollectionLearnButton(
            onClick = {},
            onCustomizeClick = {},
            modifier = Modifier.padding(top = 32.dp, end = 12.dp)
        )
    }
}
