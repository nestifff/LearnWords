package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.core.NoArgsDestination
import com.nestifff.learnwords.app.navigation.graphs.SettingsNavGraph
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.screen.settings.SettingsScreen
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel

object SettingsScreenDestination : NoArgsDestination {

    override val route: String = "${SettingsNavGraph.route}/settings"

}

fun NavGraphBuilder.settingsScreenDestination(navController: NavHostController) {

    composable(
        route = SettingsScreenDestination.route,
    ) {
        val daggerComponent = getApplication().appComponent.settingsScreenComponent().create()
        val viewModel: SettingsViewModel = daggerViewModel { daggerComponent.getViewModel() }

        SettingsScreen(
            viewModel = viewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
