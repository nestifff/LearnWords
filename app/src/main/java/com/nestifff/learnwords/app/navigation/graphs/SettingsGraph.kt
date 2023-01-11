package com.nestifff.learnwords.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.app.navigation.destinations.settingsScreenDestination

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Destination.SettingsDestination.route,
        route = Graph.SettingsGraph.route
    ) {
        settingsScreenDestination(navController)
    }
}