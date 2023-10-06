package com.nestifff.learnwords.presentation.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nestifff.learnwords.presentation.ui.theme.values.typography.wordsTypography
import com.nestifff.learnwords.presentation.ui.theme.values.PaletteMode
import com.nestifff.learnwords.presentation.ui.theme.values.colors.wordsDarkPalette
import com.nestifff.learnwords.presentation.ui.theme.values.colors.wordsLightPalette


@Composable
fun ThemeCommon(
    paletteMode: PaletteMode = PaletteMode.Light,
    content: @Composable () -> Unit
) {
    val colors = when (paletteMode) {
        PaletteMode.Dark -> wordsDarkPalette
        PaletteMode.Light -> wordsLightPalette
    }

    val typography = wordsTypography

    val rippleIndication = rememberRipple()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        val darkIcons = when (paletteMode) {
            PaletteMode.Dark -> false
            PaletteMode.Light -> true
        }
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkIcons
        )

        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = darkIcons
        )
    }

    CompositionLocalProvider(
        LocalQuizColors provides colors,
        LocalQuizTypography provides typography,
        LocalIndication provides rippleIndication,
        LocalRippleTheme provides AppRippleTheme,
        content = content
    )
}
