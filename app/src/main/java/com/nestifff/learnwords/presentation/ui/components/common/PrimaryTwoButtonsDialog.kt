package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimaryTwoButtonsDialog(
    text: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    descriptionText: String? = null,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colors.background)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = text,
                style = AppTheme.typography.h1MediumTextStyle,
                color = AppTheme.colors.content,
                textAlign = TextAlign.Center,
            )
            if (descriptionText != null) {
                Text(
                    text = descriptionText,
                    style = AppTheme.typography.h2RegularTextStyle.copy(
                        color = AppTheme.colors.contentLight
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                PrimaryButton(
                    text = negativeButtonText,
                    onClick = onNegativeClick,
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .widthIn(min = 82.dp),
                    enabledBackgroundColor = AppTheme.colors.textFieldBackground
                )
                PrimaryButton(
                    text = positiveButtonText,
                    onClick = onPositiveClick,
                    modifier = Modifier.widthIn(min = 82.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryTwoButtonsDialog_Preview() {
    ThemeProvider {
        PrimaryTwoButtonsDialog(
            text = "Do you want to sell you soul to me?",
            positiveButtonText = "Yes, sure!",
            negativeButtonText = "No, thank you...",
            onPositiveClick = {},
            onNegativeClick = {},
            onDismiss = {},
            descriptionText = "Think carefully"
        )
    }
}