package com.nestifff.learnwords.app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.ext.getApplication
import com.nestifff.learnwords.presentation.screen.learn.LearnScreen
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel

fun NavGraphBuilder.learnScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = Destination.LearnDestination.route,
    ) {

        val daggerComponent = getApplication().appComponent.learnScreenComponent().create()
        val viewModel: LearnViewModel = daggerViewModel { daggerComponent.getViewModel() }

        LearnScreen(viewModel = viewModel)
    }
}
