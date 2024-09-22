package com.nestifff.learnwords.app.navigation.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

fun scaleIntoContainer(
    isInside: Boolean = true,
    initialScale: Float = if (!isInside) 0.9f else 1.1f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(180, delayMillis = 30),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(180, delayMillis = 30))
}

fun scaleOutOfContainer(
    isInside: Boolean = false,
    targetScale: Float = if (isInside) 0.9f else 1.1f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 180,
            delayMillis = 30
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 30))
}
