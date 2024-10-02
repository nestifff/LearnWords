package com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nestifff.learnwords.presentation.screen.collection.model.CustomLearnDialogState
import com.nestifff.learnwords.presentation.ui.components.common.EditableWayToLearn
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryNumbersTextField
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.words.domain.learn.model.WayToLearnDomain

@Composable
fun CustomLearnDialog(
    state: CustomLearnDialogState,
    onNumberToLearnChange: (String) -> Unit,
    onSelectWayToLearnClick: () -> Unit,
    onWayToLearnSelect: (WayToLearnDomain) -> Unit,
    onDismissWayToLearnMenu: () -> Unit,
    onLearnClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(),
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colors.background)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Number or words:",
                    style = AppTheme.typography.h2MediumTextStyle,
                    color = AppTheme.colors.content,
                )
                PrimaryNumbersTextField(
                    value = state.numberToLearnStr,
                    onValueChange = onNumberToLearnChange,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Way to learn:",
                    style = AppTheme.typography.h2MediumTextStyle,
                    color = AppTheme.colors.content,
                )
                EditableWayToLearn(
                    currentWayToLearn = state.wayToLearn,
                    isExpandedMenuVisible = state.isWayToLearnMenuVisible,
                    onWayToLearnSelect = onWayToLearnSelect,
                    onOpenMenuClick = onSelectWayToLearnClick,
                    onMenuDismiss = onDismissWayToLearnMenu,
                )
            }

            PrimaryButton(
                text = "Learn",
                onClick = onLearnClick,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.End)
                    .width(120.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomLearnDialogPreview() {
    ThemeProvider {
        CustomLearnDialog(
            state = CustomLearnDialogState(
                numberToLearnStr = "100",
                wayToLearn = WayToLearnDomain.WRITE_TRANSLATION,
                isWayToLearnMenuVisible = false
            ),
            onNumberToLearnChange = {},
            onLearnClick = {},
            onDismiss = {},
            onSelectWayToLearnClick = {},
            onWayToLearnSelect = {},
            onDismissWayToLearnMenu = {},
        )
    }
}
