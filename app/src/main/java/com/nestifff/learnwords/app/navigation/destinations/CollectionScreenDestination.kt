package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.core.NoArgsDestination
import com.nestifff.learnwords.app.navigation.graphs.MainNavGraph
import com.nestifff.learnwords.app.navigation.graphs.SettingsNavGraph
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.screen.collection.CollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel

object CollectionScreenDestination : NoArgsDestination {

    override val route: String = "${MainNavGraph.route}/collection"

}

fun NavGraphBuilder.collectionScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = CollectionScreenDestination.route,
    ) {

        val daggerComponent = getApplication().appComponent.collectionScreenComponent().create()
        val viewModel: CollectionViewModel = daggerViewModel { daggerComponent.getViewModel() }

        CollectionScreen(
            viewModel = viewModel,
            navigateToSettingsScreen = { navController.navigate(SettingsNavGraph.prepareRoute(Unit)) },
            navigateToLearnScreen = { navController.navigate(LearnScreenDestination.prepareRoute(it)) }
        )
    }
}
