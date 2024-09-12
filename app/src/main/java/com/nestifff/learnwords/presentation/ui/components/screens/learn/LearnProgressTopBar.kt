package com.nestifff.learnwords.presentation.ui.components.screens.learn

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.learn.model.LearnProgressIndicatorState
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun LearnProgressTopBar(
    state: LearnProgressIndicatorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AppTheme.colors.backgroundMedium.copy(alpha = 0.3f))
        ) {
            val finishedPart = animateFloatAsState(targetValue = state.done.toFloat() / state.full)
            Box(
                modifier = Modifier
                    .fillMaxWidth(finishedPart.value)
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.primary)
            )
        }

        Text(
            text = "Words left: ${state.full - state.done}",
            modifier = Modifier.padding(top = 8.dp),
            style = AppTheme.typography.h2MediumTextStyle.copy(
                color = AppTheme.colors.content
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LearnProgressIndicator_Preview() {
    ThemeProvider {
        LearnProgressTopBar(
            state = LearnProgressIndicatorState(
                full = 10,
                done = 4
            )
        )
    }
}