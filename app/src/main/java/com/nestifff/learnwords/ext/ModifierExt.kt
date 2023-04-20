package com.nestifff.learnwords.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.rippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(color = WordsTheme.colors.ripple),
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
    )
}
