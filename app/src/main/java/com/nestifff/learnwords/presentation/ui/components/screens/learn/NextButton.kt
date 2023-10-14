package com.nestifff.learnwords.presentation.ui.components.screens.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun LearnButton(
    state: LearnButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (state.enabled) {
                    Modifier
                        .background(color = AppTheme.colors.secondary)
                        .clickable { onClick() }
                } else {
                    Modifier.background(color = AppTheme.colors.backgroundMedium)
                }
            )
            .padding(vertical = 12.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = state.getText(),
            style = AppTheme.typography.h2MediumTextStyle,
            color = AppTheme.colors.text,
        )
    }
}

private fun LearnButtonState.getText(): String {
    return when (this.type) {
        LearnNextButtonType.Next -> "Next"
        LearnNextButtonType.Check -> "Check"
    }
}


@Preview
@Composable
private fun LearnButtonCheckPreview() {
    ThemeProvider {
        LearnButton(
            state = LearnButtonState(true, LearnNextButtonType.Check),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun LearnButtonNextPreview() {
    ThemeProvider {
        LearnButton(
            state = LearnButtonState(true, LearnNextButtonType.Next),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun LearnButtonNextDisabledPreview() {
    ThemeProvider {
        LearnButton(
            state = LearnButtonState(false, LearnNextButtonType.Next),
            onClick = {}
        )
    }
}
