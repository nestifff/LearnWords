package com.nestifff.learnwords.presentation.screen.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nestifff.learnwords.app.navigation.SetupNavGraph

@Composable
fun RootScreen() {
    val navController = rememberNavController()

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
