package com.nestifff.learnwords.presentation.screen.root

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nestifff.learnwords.app.navigation.SetupNavGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootScreen() {
    val navController = rememberAnimatedNavController()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            SetupNavGraph(
                navController = navController,
            )
        }
    }
}
