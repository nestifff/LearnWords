package com.nestifff.learnwords.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nestifff.learnwords.app.navigation.graphs.mainGraph
import com.nestifff.learnwords.app.navigation.graphs.settingsGraph

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Graph.MainGraph.route,
    ) {
        mainGraph(navController)
        settingsGraph(navController)
    }
}
