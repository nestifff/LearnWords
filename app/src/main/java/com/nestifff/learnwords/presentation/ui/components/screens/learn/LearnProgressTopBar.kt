package com.nestifff.learnwords.presentation.ui.components.screens.learn

import android.inputmethodservice.Keyboard
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import kotlin.math.max

@Composable
fun LearnProgressTopBar(
    state: LearnProgressIndicatorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(percent = 50))
//                    .border(
//                        1.5.dp,
//                        AppTheme.colors.textFieldBackground,
//                        RoundedCornerShape(percent = 50)
//                    )
                    .background(AppTheme.colors.textFieldBackground)
            )
            // max() is used to prevent dividing by zero when data is not loaded
            val finishedPart = animateFloatAsState(
                targetValue = state.doneWordsCount.toFloat() / (max(1, state.allWordsCount))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(finishedPart.value)
                    .height(24.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(AppTheme.colors.primary)
            )
        }

        Text(
            text = "Words left: ${state.allWordsCount - state.doneWordsCount}",
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
                allWordsCount = 10,
                doneWordsCount = 4
            )
        )
    }
}