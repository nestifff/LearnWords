package com.nestifff.learnwords.app.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.presentation.screen.settings.SettingsScreen
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import com.nestifff.learnwords.presentation.screen.settings.di.DaggerSettingsComponent

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsScreenDestination(navController: NavHostController) {
    composable(
        route = Destination.SettingsDestination.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
    ) {
        val daggerComponent = DaggerSettingsComponent.builder().build()
        val viewModel: SettingsViewModel = daggerViewModel { daggerComponent.getViewModel() }
        SettingsScreen(navController = navController, viewModel = viewModel)
    }
}
