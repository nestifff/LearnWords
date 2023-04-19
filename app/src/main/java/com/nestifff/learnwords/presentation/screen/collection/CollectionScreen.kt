package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.presentation.ui.components.screens.collection.*
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.WordsList
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    navController: NavHostController,
    vm: CollectionViewModel
) {

    val viewState = vm.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WordsTheme.colors.background)
    ) {

        Column {
            CollectionTopBar(
                modifier = Modifier.padding(top = 3.dp, end = 4.dp),
                settingsButtonClicked = { navController.navigate(Graph.SettingsGraph.route) }
            )
            Box() {
                WordsList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    words = viewState.value.words,
                    saveClicked = { updatedWord ->
                        vm.onEvent(CollectionViewModel.Event.WordUpdated(updatedWord))
                    },
                    deleteWordTriggered = {
                        vm.onEvent(CollectionViewModel.Event.WordDeleted(it))
                    }
                )
                CollectionsSwitcher(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    collections = listOf("In progress", "Learned", "Favorites")
                )
            }
        }

        CollectionLearnButton(
            modifier = Modifier
                .padding(bottom = 68.dp, end = 10.dp)
                .align(Alignment.BottomEnd)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AddWordDialog(
                onEnterWord = { rus, eng ->
                    vm.onEvent(CollectionViewModel.Event.WordAdded(rus = rus, eng = eng))
                }
            )
        }
    }
}
