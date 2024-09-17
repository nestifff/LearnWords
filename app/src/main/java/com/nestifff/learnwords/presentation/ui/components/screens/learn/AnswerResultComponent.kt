package com.nestifff.learnwords.presentation.ui.components.screens.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nestifff.learnwords.R
import com.nestifff.learnwords.presentation.screen.learn.model.UserAnswerResultState
import com.nestifff.learnwords.presentation.screen.learn.model.UserAnswerResultState.Correct
import com.nestifff.learnwords.presentation.screen.learn.model.UserAnswerResultState.CorrectWithTypo
import com.nestifff.learnwords.presentation.screen.learn.model.UserAnswerResultState.Wrong
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun AnswerResultComponent(
    state: UserAnswerResultState?,
    modifier: Modifier = Modifier,
) {
    val correctAnimationComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_success)
    )
    val wrongAnimationComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_failure)
    )

    Box(
        modifier = modifier
            .height(82.dp)
            .fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = state is Wrong || state is CorrectWithTypo,
            modifier = Modifier.fillMaxHeight().padding(start = 68.dp),
            enter = fadeIn(tween(200, 600)),
            exit = fadeOut(tween(50))
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Correct:",
                    style = AppTheme.typography.h1RegularTextStyle,
                )
                Text(
                    text = state?.getCorrectAnswerOrNull() ?: "",
                    style = AppTheme.typography.h1BoldTextStyle,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = state?.getWasMovedToLearnedOrNull() == true,
            modifier = Modifier.padding(top = 3.dp),
            enter = fadeIn(tween(200, 600)),
            exit = fadeOut(tween(50))
        ) {
            Text(
                text = "Was moved to Learned! \uD83C\uDF89",
                style = AppTheme.typography.h3MediumTextStyle,
            )
        }


        when (state) {
            is Correct, is CorrectWithTypo -> {
                LottieAnimation(
                    composition = correctAnimationComposition,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(78.dp)
                        .offset(x = (-16).dp),
                    speed = 1.5f
                )
            }

            is Wrong -> {
                LottieAnimation(
                    composition = wrongAnimationComposition,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(72.dp)
                        .offset(x = -(12).dp)
                        .alpha(0.8f),
                    speed = 2.5f
                )
            }

            null -> Unit
        }
    }
}
