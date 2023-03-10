package com.nestifff.learnwords.app.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.nestifff.learnwords.app.App
import com.nestifff.learnwords.app.di.utils.daggerViewModel
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.ext.getActivity
import com.nestifff.learnwords.presentation.screen.collection.CollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.collectionScreenDestination(
    navController: NavHostController,
) {
    composable(
        route = Destination.CollectionDestination.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
    ) {
        // TODO create getApplication() instead of getActivity()
        val daggerComponent = (LocalContext.current.getActivity()!!.application as App)
            .appComponent.collectionComponent().create()
        val viewModel: CollectionViewModel = daggerViewModel { daggerComponent.getViewModel() }
        CollectionScreen(navController = navController, vm = viewModel)
    }
}
