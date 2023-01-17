package com.nestifff.learnwords.app.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.nestifff.learnwords.app.navigation.Destination
import com.nestifff.learnwords.presentation.screen.collection.CollectionScreen

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
        CollectionScreen()
    }
}
