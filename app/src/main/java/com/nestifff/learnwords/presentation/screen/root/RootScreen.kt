package com.nestifff.learnwords.presentation.screen.root

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nestifff.learnwords.app.navigation.SetupNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        SetupNavGraph(
            navController = navController,
        )
    }
}
