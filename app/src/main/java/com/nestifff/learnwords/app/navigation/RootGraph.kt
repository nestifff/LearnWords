package com.nestifff.learnwords.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.nestifff.learnwords.app.navigation.graphs.mainGraph
import com.nestifff.learnwords.app.navigation.graphs.settingsGraph

private const val RootRoute = "root_route"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Graph.MainGraph.route,
        route = RootRoute,
    ) {
        mainGraph(navController)
        settingsGraph(navController)
    }
}
