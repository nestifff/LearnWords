package com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.screen.collection.model.CustomLearnDialogState
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun CustomLearnDialog(
    state: CustomLearnDialogState,
    onNumberToLearnChange: (Int) -> Unit,
    onLearnClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state is CustomLearnDialogState.Expanded) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppTheme.colors.background)
                    .fillMaxWidth(0.75f)
                    .padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Number:",
                        style = AppTheme.typography.h2MediumTextStyle,
                        color = AppTheme.colors.text,
                    )
                    WordsTextField(
                        value = state.numberToLearn.toString(),
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                onNumberToLearnChange(it.toInt())
                            }
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(64.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Way to learn: todo",
                        style = AppTheme.typography.h2MediumTextStyle,
                        color = AppTheme.colors.text,
                    )
                }

                PrimaryButton(
                    onClick = onLearnClick,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.End)
                        .width(120.dp)
                ) {
                    Text(
                        text = "Learn",
                        style = AppTheme.typography.h2MediumTextStyle,
                        color = AppTheme.colors.text,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomLearnDialogPreview() {
    ThemeProvider {
        CustomLearnDialog(
            state = CustomLearnDialogState.Expanded(
                numberToLearn = 0,
                wayToLearn = WayToLearn.EngToRus
            ),
            onNumberToLearnChange = {},
            onLearnClick = {},
            onDismiss = {},
        )
    }
}
