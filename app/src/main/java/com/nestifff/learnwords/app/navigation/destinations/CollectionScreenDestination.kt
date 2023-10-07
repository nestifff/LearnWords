package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.screen.collection.CollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel

fun NavGraphBuilder.collectionScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = Destination.CollectionDestination.route,
    ) {

        val daggerComponent = getApplication().appComponent.collectionScreenComponent().create()
        val viewModel: CollectionViewModel = daggerViewModel { daggerComponent.getViewModel() }

        CollectionScreen(
            viewModel = viewModel,
            navigateToSettingsScreen = { navController.navigate(Graph.SettingsGraph.route) },
            navigateToLearnScreen = { navController.navigate(Destination.CollectionDestination.route) }
        )
    }
}
