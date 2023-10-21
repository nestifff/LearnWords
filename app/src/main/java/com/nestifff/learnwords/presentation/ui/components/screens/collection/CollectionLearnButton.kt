package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionLearnButton(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val haptic = LocalHapticFeedback.current
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(color = AppTheme.colors.primary)
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick()
                },
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = AppTheme.colors.icons
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CollectionLearnButtonPreview() {
    ThemeProvider {
        CollectionLearnButton(
            onClick = {},
            onLongClick = {},
        )
    }
}
