package com.nestifff.learnwords.presentation.ui.theme.values.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorCommon(
    val background: Color,
    val backgroundLight: Color,
    val backgroundMedium: Color,
    val content: Color,
    val contentOnPrimary: Color,
    val contentLight: Color,
    val ripple: Color,
    val primary: Color,
    val primaryLight: Color,
    val secondary: Color,
    val warning: Color,
    val textFieldBackground: Color,
    val popupBackground: Color, // surface in material theme
    val sectionDivider: Color,
    val switcherThumb: Color,
)
