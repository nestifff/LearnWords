package com.nestifff.learnwords.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = AppTheme.colors.primary))
}
