package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.presentation.ui.components.screens.collection.*
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
                    // TODO it might have problems (list in state class)
                    words = viewModel.state.words,
                    selectedIndex = selectedWordInd,
                    onNewSelected = { ind ->
                        selectedWordInd = if (selectedWordInd == ind) null else ind
                    },
                    saveClicked = {}
                )
                CollectionsSwitcher(
                    modifier = Modifier.padding(horizontal = 10.dp),
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
                    .padding(bottom = 68.dp, end = 10.dp)
                    .align(Alignment.End)
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AddWordDialog(onEnterWord = {})
        }
    }
}
