package com.nestifff.learnwords.presentation.ui.theme

import androidx.compose.material.LocalContentColor
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
object AppRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = LocalContentColor.current

    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = StateTokens.PressedStateLayerOpacity,
    focusedAlpha = StateTokens.FocusStateLayerOpacity,
    draggedAlpha = StateTokens.DraggedStateLayerOpacity,
    hoveredAlpha = StateTokens.HoverStateLayerOpacity
)

internal object StateTokens {
    const val DraggedStateLayerOpacity = 0.16f
    const val FocusStateLayerOpacity = 0.12f
    const val HoverStateLayerOpacity = 0.08f
    const val PressedStateLayerOpacity = 0.12f
}
