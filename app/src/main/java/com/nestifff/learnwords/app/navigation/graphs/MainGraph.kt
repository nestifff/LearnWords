package com.nestifff.learnwords.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.app.navigation.destinations.collectionScreenDestination
import com.nestifff.learnwords.app.navigation.destinations.learnScreenDestination

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Destination.CollectionDestination.route,
        route = Graph.MainGraph.route
    ) {
        collectionScreenDestination(navController)
        learnScreenDestination(navController)
    }
}
