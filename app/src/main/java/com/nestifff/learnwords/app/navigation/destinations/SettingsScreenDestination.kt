package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.presentation.screen.settings.SettingsScreen
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import com.nestifff.learnwords.presentation.screen.settings.di.DaggerSettingsComponent

fun NavGraphBuilder.settingsScreenDestination(navController: NavHostController) {

    composable(
        route = Destination.SettingsDestination.route,
    ) {
        val daggerComponent = DaggerSettingsComponent.builder().build()
        val viewModel: SettingsViewModel = daggerViewModel { daggerComponent.getViewModel() }

        SettingsScreen(navController = navController, viewModel = viewModel)
    }
}
