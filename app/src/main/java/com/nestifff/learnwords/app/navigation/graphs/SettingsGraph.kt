package com.nestifff.learnwords.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.nestifff.learnwords.app.navigation.core.NavGraphRoot
import com.nestifff.learnwords.app.navigation.destinations.SettingsScreenDestination
import com.nestifff.learnwords.app.navigation.destinations.settingsScreenDestination

object SettingsNavGraph : NavGraphRoot<Unit> {
    override val route: String = "settings"
    override val startingDestination = SettingsScreenDestination
}

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = SettingsNavGraph.startingDestination.route,
        route = SettingsNavGraph.route
    ) {
        settingsScreenDestination(navController)
    }
}
