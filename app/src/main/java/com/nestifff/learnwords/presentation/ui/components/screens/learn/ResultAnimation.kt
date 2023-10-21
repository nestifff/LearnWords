package com.nestifff.learnwords.presentation.ui.components.screens.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.learn.model.LearnResultAnimationState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnResultAnimationState.Right
import com.nestifff.learnwords.presentation.screen.learn.model.LearnResultAnimationState.Wrong
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun ResultAnimation(
    state: LearnResultAnimationState,
    onAnimationFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(
                color = when (state) {
                    is Right -> Color.Green
                    is Wrong -> Color.Red
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        val text: String? = when {
            state is Right && state.wasMovedToLearned -> "Was moved to learned"
            state is Wrong -> state.rightAnswer
            else -> null
        }
        if (text != null) {
            Text(
                text = text,
                style = AppTheme.typography.h2MediumTextStyle,
                color = AppTheme.colors.text
            )
        }
    }
}
