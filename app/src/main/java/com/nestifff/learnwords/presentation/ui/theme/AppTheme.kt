package com.nestifff.learnwords.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.nestifff.learnwords.presentation.ui.theme.values.typography.TypographyCommon
import com.nestifff.learnwords.presentation.ui.theme.values.colors.ColorCommon

const val ColorProviderError = "No colors provided"
const val TypographyProviderError = "No typography provided"

object AppTheme {
    val colors: ColorCommon
        @Composable
        @ReadOnlyComposable
        get() = LocalQuizColors.current

    val typography: TypographyCommon
        @Composable
        @ReadOnlyComposable
        get() = LocalQuizTypography.current
}

val LocalQuizColors = staticCompositionLocalOf<ColorCommon> {
    error(ColorProviderError)
}

val LocalQuizTypography = staticCompositionLocalOf<TypographyCommon> {
    error(TypographyProviderError)
}
