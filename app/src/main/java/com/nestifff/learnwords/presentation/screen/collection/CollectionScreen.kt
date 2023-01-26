package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionLearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionsSwitcher
import com.nestifff.learnwords.presentation.ui.components.screens.collection.WordsList
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionScreen(
    navController: NavHostController,
    viewModel: CollectionViewModel
) {

    var selectedWordInd: Int? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WordsTheme.colors.background)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            CollectionTopBar(
                modifier = Modifier.padding(top = 8.dp),
                settingsButtonClicked = { navController.navigate(Graph.SettingsGraph.route) }
            )
            Box() {
                WordsList(
                    modifier = Modifier.fillMaxSize(),
                    // TODO may have problems (list in state class)
                    words = viewModel.state.words,
                    selectedIndex = selectedWordInd,
                    onNewSelected = { ind ->
                        selectedWordInd = if (selectedWordInd == ind) null else ind
                    },
                    saveClicked = {}
                )
                CollectionsSwitcher(
                    collections = listOf("In progress", "Learned", "Favorites")
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            CollectionLearnButton(
                modifier = Modifier
                    .padding(bottom = 23.dp, end = 16.dp)
                    .align(Alignment.End)
            )
        }
    }
}
