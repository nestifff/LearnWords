package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.core.NoArgsDestination
import com.nestifff.learnwords.app.navigation.graphs.MainNavGraph
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.screen.result.ResultScreen
import com.nestifff.learnwords.presentation.screen.result.ResultViewModel

object ResultScreenDestination : NoArgsDestination {

    override val route: String = "${MainNavGraph.route}/result"
}

fun NavGraphBuilder.resultScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = ResultScreenDestination.route,
    ) {

        val daggerComponent = getApplication().appComponent.resultScreenComponent().create()
        val viewModel: ResultViewModel = daggerViewModel { daggerComponent.getViewModel() }

        ResultScreen(
            viewModel = viewModel,
            navigateToCollectionScreen = {
                navController.popBackStack(CollectionScreenDestination.prepareRoute(Unit), false)
            }
        )
    }
}
