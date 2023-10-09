package com.nestifff.learnwords.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.nestifff.learnwords.app.navigation.core.NavGraphRoot
import com.nestifff.learnwords.app.navigation.destinations.CollectionScreenDestination
import com.nestifff.learnwords.app.navigation.destinations.collectionScreenDestination
import com.nestifff.learnwords.app.navigation.destinations.learnScreenDestination

object MainNavGraph : NavGraphRoot<Unit> {
    override val route: String = "main"
    override val startingDestination = CollectionScreenDestination
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = MainNavGraph.startingDestination.route,
        route = MainNavGraph.route
    ) {
        collectionScreenDestination(navController)
        learnScreenDestination(navController)
    }
}
