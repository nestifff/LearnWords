package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.learn.model.WayToLearnDomain

@Composable
fun EditableWayToLearn(
    currentWayToLearn: WayToLearnDomain,
    isExpandedMenuVisible: Boolean,
    onWayToLearnSelect: (WayToLearnDomain) -> Unit,
    onOpenMenuClick: () -> Unit,
    onMenuDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onOpenMenuClick() }
                .background(AppTheme.colors.textFieldBackground)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp, end = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentWayToLearn.getTitle(),
                style = AppTheme.typography.h2RegularTextStyle,
                color = AppTheme.colors.content,
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp)),
                tint = AppTheme.colors.content,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = isExpandedMenuVisible,
            onDismissRequest = onMenuDismiss,
            modifier = Modifier.background(AppTheme.colors.popupBackground)
        ) {
            WayToLearnDomain.entries.forEach { wayToLearn ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = wayToLearn.getTitle(),
                            style = AppTheme.typography.h2RegularTextStyle,
                            color = AppTheme.colors.content,
                        )
                    },
                    onClick = { onWayToLearnSelect(wayToLearn) }
                )
            }
        }
    }
}

private fun WayToLearnDomain.getTitle(): String {
    return when (this) {
        WayToLearnDomain.WRITE_LEARNING_VALUE -> "Write learning word"
        WayToLearnDomain.WRITE_TRANSLATION -> "Write translation"
    }
}

@Preview(showBackground = true)
@Composable
private fun EditableWayToLearn_Preview() {
    ThemeProvider {
        EditableWayToLearn(
            currentWayToLearn = WayToLearnDomain.WRITE_TRANSLATION,
            isExpandedMenuVisible = true,
            onWayToLearnSelect = {},
            onOpenMenuClick = {},
            onMenuDismiss = {},
        )
    }
}
